package io.devfactory.example.core.app.sample.aop;

import io.devfactory.example.core.app.sample.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import static java.util.Objects.nonNull;

@Slf4j
@Aspect
public class RetryAspect {

  @Around("@annotation(retry)")
  public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
    log.info("[retry] {} retry={}", joinPoint.getSignature(), retry);

    int maxRetry = retry.value();
    Exception exceptionHolder = null;

    for (int retryCount = 1; retryCount <= maxRetry; retryCount += 1) {
      try {
        log.info("[retry] try count={}/{}", retryCount, maxRetry);
        return joinPoint.proceed();
      } catch (Exception e) {
        exceptionHolder = e;
      }
    }

    if (nonNull(exceptionHolder)) {
      throw exceptionHolder;
    }

    return joinPoint.proceed();
  }

}
