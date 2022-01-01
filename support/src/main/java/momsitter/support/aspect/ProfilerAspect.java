package momsitter.support.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ProfilerAspect {

  @Pointcut("execution(public * org.springframework.data.repository.Repository+.*(..))")
  public void monitor() {
    /* no action needed */
  }

  @Around("monitor()")
  public Object profile(ProceedingJoinPoint pjp) throws Throwable {
    long start = System.currentTimeMillis();
    try {
      return pjp.proceed();
    } finally {
      long elapsedTime = System.currentTimeMillis() - start;
      log.debug(
          pjp.getSignature() + ": Execution time: " + elapsedTime + " ms. (" + elapsedTime / 60000
              + " minutes)");
    }
  }
}
