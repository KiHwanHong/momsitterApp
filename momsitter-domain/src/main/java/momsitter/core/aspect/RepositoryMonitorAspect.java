package momsitter.core.aspect;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@EnableAspectJAutoProxy
@Aspect
@Component
@Profile({"local", "dev", "beta"})
public class RepositoryMonitorAspect {

  private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryMonitorAspect.class);
  private static final String SLOW_REPOSITORY_EXECUTION_MESSAGE =
      "\n==================================================="
          + "\nSLOW QUERY EXECUTED {} !!!"
          + "\nMethod Name : {}, "
          + "\nArguments   : {}, "
          + "\nElapsed Time: {}ms"
          + "\n===================================================";

  @Pointcut("execution(* momsitter..*Repository*.*(..))")
  public void repositoryExecutionPointcut() {
    // Repository Execution Pointcut definition
  }

  @Around("repositoryExecutionPointcut()")
  public Object aroundRepositoryExecution(ProceedingJoinPoint proceedingJoinPoint)
      throws Throwable {
    long startTime = System.nanoTime();
    try {
      Object result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());

      logSlowMessage(proceedingJoinPoint, startTime, "Successfully");

      return result;
    } catch (Throwable e) {
      logSlowMessage(proceedingJoinPoint, startTime, "With Exception");
      throw e;
    }
  }

  private static void logSlowMessage(ProceedingJoinPoint proceedingJoinPoint, long startTime,
      String result) {
    long elapsedTime = (System.nanoTime() - startTime) / 1000_000;
    if (elapsedTime > 5000) {
      LOGGER.info(SLOW_REPOSITORY_EXECUTION_MESSAGE,
          result,
          proceedingJoinPoint.getSignature(),
          Arrays.stream(proceedingJoinPoint.getArgs()).collect(Collectors.toList()),
          elapsedTime);
    }
  }
}
