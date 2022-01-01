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
          def targetGroupArn = deploySupport.getTargetGroupArn(AWS_ACCOUNT_NAME, APPLICATION_NAME, DEPLOYMENT_GROUP_NAME)
          TARGET_GROUP_ARN = targetGroupArn
        }
      }
    }


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


    stage('deploy to CodeDeploy') {
      steps {
        script {
          ROLLBACK_REVISION = deploySupport.getRollBackRevision(APPLICATION_NAME, DEPLOYMENT_GROUP_NAME, ROLLBACK_TIMESTAMP)
          def deployId = deploySupport.deployByRevision(AWS_ACCOUNT_NAME, APPLICATION_NAME, DEPLOYMENT_GROUP_NAME, TARGET_ASG, S3_BUCKET, S3_FILE_PATH, ROLLBACK_REVISION)
          DEPLOY_ID = deployId
          sendSimploySlack("SUCCESS", "Deployment started.\ndeployId:[${DEPLOY_ID}]")
          deploySupport.waitDeployReady(AWS_ACCOUNT_NAME, DEPLOY_ID)
        }
      }
    }

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
  }

    post {
      success {
        script {
          deploySupport.pushHistory(APPLICATION_NAME, DEPLOYMENT_GROUP_NAME, PROFILE, ROLLBACK_REVISION, AWS_REGION, "RollBack", AWS_ACCOUNT_NAME, AWS_ACCOUNT_ID, TARGET_ASG)
        }
        sendSimploySlack("SUCCESS", "RollBack Finished")
      }
      failure {
        script {
          deploySupport.pushHistory(APPLICATION_NAME, DEPLOYMENT_GROUP_NAME, PROFILE, "N/A", AWS_REGION, "RollBack FAILURE", AWS_ACCOUNT_NAME, AWS_ACCOUNT_ID, env.TARGET_ASG)
        }
        sendSimploySlack("FAILURE", "${env.FAILURE_MESSAGE}")
        echo "\n\n\n>>> Deploy Failure: ${env.FAILURE_MESSAGE}\n\n\n"
      }
      aborted {
        script {
          deploySupport.pushHistory(APPLICATION_NAME, DEPLOYMENT_GROUP_NAME, PROFILE, "N/A", AWS_REGION, "RollBack ABORTED", AWS_ACCOUNT_NAME, AWS_ACCOUNT_ID, env.TARGET_ASG)
        }
        sendSimploySlack("ABORTED", 'RollBack aborted')
      }
    }

    environment {
      // Do not change
      APPLICATION_NAME = "${application_name}"
      MODULE_NAME = "${module_name}"
      PROFILE = "${profile}"
      ROLLBACK_TIMESTAMP = "${rollback_version}"
      DEPLOYMENT_GROUP_NAME = "${deployment_group_name ? deployment_group_name : module_name}"
      TEMP_DIR = "temp_${DEPLOYMENT_GROUP_NAME}"
      BLUE_ASG_NAME = "${APPLICATION_NAME}-${DEPLOYMENT_GROUP_NAME}-blue"
      GREEN_ASG_NAME = "${APPLICATION_NAME}-${DEPLOYMENT_GROUP_NAME}-green"
      S3_FILE_PATH = "${APPLICATION_NAME}/${DEPLOYMENT_GROUP_NAME}"
      ENABLE_CANARY_DEPLOY = "${enable_canary_deploy}"
      ENABLE_RELEASE_COMFIRM = "${enable_release_confirm}"
      AWS_ACCOUNT_NAME = "${env.DEPLOY_TARGET_AWS_ACCOUNT}"
      AWS_ACCOUNT_ID = "${env.DEPLOY_TARGET_AWS_ACCOUNT_ID}"
      S3_BUCKET = "woowa-platform-codedeploy-${AWS_ACCOUNT_NAME}"
      AWS_REGION = "ap-northeast-2"
      RELEASE_SCOPE = "Switch"
      CURRENT_ASG = ""
      TARGET_ASG = ""
      DEPLOY_ID = ""
      TARGET_GROUP_ARN = ""
      ROLLBACK_REVISION = ""
    }
}
