@Library('simploy@v5') _

def deploySupport
if ("${profile}" == "prod") {
  deploySupport = jenkinsSupport.deploySupport(false)
} else {
  deploySupport = jenkinsSupport.deploySupport(true)
}

pipeline {
  agent { label 'jenkins-slave-jdk11' }

  stages {
    stage('check current status') {
      steps {
        script {
          deploySupport.setAWSProfile(AWS_ACCOUNT_NAME, AWS_ACCOUNT_ID, AWS_REGION)

          if (deploySupport.checkDeployInprogress(AWS_ACCOUNT_NAME, APPLICATION_NAME, DEPLOYMENT_GROUP_NAME)) {
            deploySupport.exitPipeline("Deploy in progress already.\nPlease stop deploy in AWS console.")
          }

          def currentASG = deploySupport.getCurrentASGName(AWS_ACCOUNT_NAME, APPLICATION_NAME, DEPLOYMENT_GROUP_NAME)
          CURRENT_ASG = currentASG
          def targetASG = deploySupport.getTargetASGName(CURRENT_ASG, BLUE_ASG_NAME, GREEN_ASG_NAME)
          TARGET_ASG = targetASG
          echo ">>> Deploy ${CURRENT_ASG} to ${TARGET_ASG}"
          def targetGroup = deploySupport.describeTargetGroup(AWS_ACCOUNT_NAME, APPLICATION_NAME, DEPLOYMENT_GROUP_NAME)
          TARGET_GROUP_ARN = targetGroup.TargetGroupArn   // code deploy 의 ELB 를 조회해서, ELB 의 TG 목록으로 확인한다.
          TARGET_GROUP_NAME = targetGroup.TargetGroupName
        }
      }
    }

    stage('pre-deploy') {
      parallel {
        stage('create target ASG instances') {
          steps {
            script {
              def currentASGCapacity = deploySupport.getASGCapacity(AWS_ACCOUNT_NAME, CURRENT_ASG)
              deploySupport.prepareTargeASGInstances(AWS_ACCOUNT_NAME, TARGET_ASG, currentASGCapacity)

              def desiredCapacitySum = currentASGCapacity[2].toInteger() + currentASGCapacity[5].toInteger()
              deploySupport.checkInstanceStatus(AWS_ACCOUNT_NAME, TARGET_ASG, desiredCapacitySum)

              sendSimploySlack("SUCCESS", "Target ASGs are ready to the deploy.\nTargetASG:[${TARGET_ASG}]\n DesiredCapacity:[${desiredCapacitySum}]")
            }
          }
        }

        stage('build artifacts & upload to s3') {
          steps {
            script {
              deploySupport.buildGradle(GRADLE_CMD)
              deploySupport.copyAppSpecAndJar(MODULE_NAME, TEMP_DIR, ARTIFACT_FILE)
              deploySupport.updateAppSpecConfigValue(TEMP_DIR, PROFILE, APPLICATION_NAME, MODULE_NAME, ENV_JSON_TEXT)

              def deployCommand = deploySupport.uploadS3(AWS_ACCOUNT_NAME, APPLICATION_NAME, S3_BUCKET, S3_FILE_PATH, TEMP_DIR)
              def currentBuildRevision = deploySupport.getBuildRevision(deployCommand)
              def rollback_revision = deploySupport.getCurrentRevision(AWS_ACCOUNT_NAME, APPLICATION_NAME, DEPLOYMENT_GROUP_NAME)

              CURRENT_BUILD_REVISION = currentBuildRevision
              ROLLBACK_REVISION = rollback_revision
              echo "current deploy reivision : ${CURRENT_BUILD_REVISION}"
              echo "rollback revision : ${ROLLBACK_REVISION}"
              sendSimploySlack("SUCCESS", "Build complete.\nrevision:[${CURRENT_BUILD_REVISION}]\nrollback revision:[${ROLLBACK_REVISION}]")
            }
          }
        }
      }
    }

    stage('deploy to CodeDeploy') {
      steps {
        script {
          def deployId = deploySupport.deployByRevision(AWS_ACCOUNT_NAME, APPLICATION_NAME, DEPLOYMENT_GROUP_NAME, TARGET_ASG, S3_BUCKET, S3_FILE_PATH, CURRENT_BUILD_REVISION)
          DEPLOY_ID = deployId
          sendSimploySlack("SUCCESS", "Deployment started.\ndeployId:[${DEPLOY_ID}]")
          deploySupport.waitDeployReady(AWS_ACCOUNT_NAME, DEPLOY_ID)
        }
      }
    }

    stage('staged') {
      when {
        expression { ENABLE_STAGE_DEPLOY == "true" }
      }
      steps {
        script {
          def stageTargetGroupArn

          try {
            def ec2 = deploySupport.descrbieLeaderASGEC2(AWS_ACCOUNT_NAME, TARGET_ASG)
            stageTargetGroupArn = deploySupport.getStageTargetGroupArn(AWS_ACCOUNT_NAME, TARGET_GROUP_NAME)
            deploySupport.attachLeaderAsgToTargetGroup(AWS_ACCOUNT_NAME, TARGET_ASG, stageTargetGroupArn)

            sendSimploySlack("SUCCESS", """\
Stage Deployment complete.
TargetASG: ${ec2.asgName}
InstanceId: ${ec2.instanceId}
PrivateIp: ${ec2.privateIpAddress}
""")
            input message: """\
Staged
ASG         : ${ec2.asgName}
Instance-id : ${ec2.instanceId} 
private-ip  : ${ec2.privateIpAddress}"""
          } catch (e) {
            println e
            ENABLE_CANARY_DEPLOY = "false"
            RELEASE_SCOPE = "StopDeploy"
          }

          deploySupport.detachLeaderAsgFromTargetGroup(AWS_ACCOUNT_NAME, TARGET_ASG, stageTargetGroupArn)
        }
      }
    }

    stage('canary') {
      when {
        expression { ENABLE_CANARY_DEPLOY == "true" }
      }
      steps {
        script {
          deploySupport.attachLeaderAsgToTargetGroup(AWS_ACCOUNT_NAME, TARGET_ASG, TARGET_GROUP_ARN)
        }
      }
    }

    stage('switch confirm') {
      when {
        expression { ENABLE_CANARY_DEPLOY == "true" }
      }
      steps {
        script {
          try {
            sendSimploySlack("SUCCESS", "Canary deployment complete\ndeployId:[${DEPLOY_ID}]\nplease confirm the blue/green switching")
            input message: "Deploy continue?"
            RELEASE_SCOPE = "Switch"
          } catch (e) {
            RELEASE_SCOPE = "StopDeploy"
          }

          deploySupport.detachLeaderAsgFromTargetGroup(AWS_ACCOUNT_NAME, TARGET_ASG, TARGET_GROUP_ARN)
        }
      }
    }

    stage('switch') {
      parallel {
        stage('Blue/Green switch') {
          when {
            expression { RELEASE_SCOPE == 'Switch' }
          }
          steps {
            script {
              deploySupport.reroute(AWS_ACCOUNT_NAME, DEPLOY_ID)
              deploySupport.waitDeploySuccess(AWS_ACCOUNT_NAME, DEPLOY_ID)
              RELEASE_SCOPE = "CleanUp"
            }
          }
        }
        stage('stop deploy') {
          when {
            expression { RELEASE_SCOPE == 'StopDeploy' }
          }
          steps {
            script {
              deploySupport.deployStop(AWS_ACCOUNT_NAME, DEPLOY_ID)
              deploySupport.setASGCapacityToZero(AWS_ACCOUNT_NAME, TARGET_ASG)
              deploySupport.exitPipeline("Deploy is stopped manually.")
            }
          }
        }
      }
    }

    stage('deploy confirm') {
      when {
        expression {
          ENABLE_RELEASE_COMFIRM == "true"
        }
      }
      steps {
        script {
          try {
            sendSimploySlack("SUCCESS", "reroute complete\ndeployId:[${DEPLOY_ID}]\nplease confirm the clean up")
            def instanceList = deploySupport.describeDeployTargetAsgInstances(AWS_ACCOUNT_NAME, TARGET_ASG)
            input message: """\
Deploy continue?
Deploy target Instance List
${deploySupport.beautifyInstanceListText(instanceList)}"""
          } catch (e) {
            RELEASE_SCOPE = "Rollback"
          }
        }
      }
    }

    stage('clean up') {
      parallel {
        stage('clean origin ASG') {
          when {
            expression {
              RELEASE_SCOPE == "CleanUp"
            }
          }
          steps {
            script {
              deploySupport.setASGCapacityToZero(AWS_ACCOUNT_NAME, CURRENT_ASG)
            }
          }
        }
        stage('rollback & clean target ASG') {
          when {
            expression {
              RELEASE_SCOPE == "Rollback"
            }
          }
          steps {
            script {
              def deployId = deploySupport.deployByRevision(AWS_ACCOUNT_NAME, APPLICATION_NAME, DEPLOYMENT_GROUP_NAME, CURRENT_ASG, S3_BUCKET, S3_FILE_PATH, ROLLBACK_REVISION)
              ROLLBACK_DEPLOY_ID = deployId
              deploySupport.waitDeployReady(AWS_ACCOUNT_NAME, ROLLBACK_DEPLOY_ID)
              deploySupport.reroute(AWS_ACCOUNT_NAME, ROLLBACK_DEPLOY_ID)
              deploySupport.waitDeploySuccess(AWS_ACCOUNT_NAME, ROLLBACK_DEPLOY_ID)
              deploySupport.setASGCapacityToZero(AWS_ACCOUNT_NAME, TARGET_ASG)
              deploySupport.exitPipeline("Deployment rolled back")
            }
          }
        }
      }
    }
  }

  post {
    success {
      script {
        deploySupport.pushHistory(APPLICATION_NAME, DEPLOYMENT_GROUP_NAME, PROFILE, CURRENT_BUILD_REVISION, AWS_REGION, RELEASE_SCOPE, AWS_ACCOUNT_NAME, AWS_ACCOUNT_ID, TARGET_ASG)
      }
      sendSimploySlack("SUCCESS", "Deployment Finished")
    }
    failure {
      script {
        deploySupport.pushHistory(APPLICATION_NAME, DEPLOYMENT_GROUP_NAME, PROFILE, "N/A", AWS_REGION, "FAILURE", AWS_ACCOUNT_NAME, AWS_ACCOUNT_ID, env.TARGET_ASG)
      }
      sendSimploySlack("FAILURE", "${env.FAILURE_MESSAGE}")
      echo "\n\n\n>>> Deploy Failure: ${env.FAILURE_MESSAGE}\n\n\n"
    }
    aborted {
      script {
        deploySupport.pushHistory(APPLICATION_NAME, DEPLOYMENT_GROUP_NAME, PROFILE, "N/A", AWS_REGION, "ABORTED", AWS_ACCOUNT_NAME, AWS_ACCOUNT_ID, env.TARGET_ASG)
      }
      sendSimploySlack("ABORTED", 'Deployment aborted')
    }
  }

  environment {
    // Do not change
    ENV_JSON_TEXT = "${env_json_text}"
    APPLICATION_NAME = "${application_name}"
    MODULE_NAME = "${module_name}"
    PROFILE = "${profile}"
    ARTIFACT_FILE = "${artifact_file}"
    DEPLOYMENT_GROUP_NAME = "${deployment_group_name ? deployment_group_name : module_name}"
    TEMP_DIR = "temp_${DEPLOYMENT_GROUP_NAME}"
    BLUE_ASG_NAME = "${APPLICATION_NAME}-${DEPLOYMENT_GROUP_NAME}-blue"
    GREEN_ASG_NAME = "${APPLICATION_NAME}-${DEPLOYMENT_GROUP_NAME}-green"
    S3_FILE_PATH = "${APPLICATION_NAME}/${DEPLOYMENT_GROUP_NAME}"
    ENABLE_STAGE_DEPLOY = "${enable_stage_deploy}"
    ENABLE_CANARY_DEPLOY = "${enable_canary_deploy}"
    ENABLE_RELEASE_COMFIRM = "${enable_release_confirm}"
    AWS_ACCOUNT_NAME = "${env.DEPLOY_TARGET_AWS_ACCOUNT}"
    AWS_ACCOUNT_ID = "${env.DEPLOY_TARGET_AWS_ACCOUNT_ID}"
    S3_BUCKET = "woowa-platform-codedeploy-${AWS_ACCOUNT_NAME}"
    AWS_REGION = "ap-northeast-2"
    RELEASE_SCOPE = "Switch"
    GRADLE_CMD = "GRADLE_OPTS=-Xmx2048m ./gradlew :${MODULE_NAME}:clean :${MODULE_NAME}:build --parallel -PgradleBuildType=jar -Dorg.gradle.daemon=false"
    CURRENT_ASG = ""
    TARGET_ASG = ""
    DEPLOY_ID = ""
    TARGET_GROUP_NAME = ""
    TARGET_GROUP_ARN = ""
    ROLLBACK_DEPLOY_ID = ""
    DEPLOY_COMMAND = ""
    CURRENT_BUILD_REVISION = ""
    ROLLBACK_REVISION = ""
  }
}
