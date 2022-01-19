package io.devfactory.example.core.config.v6_aop.aspect;

import io.devfactory.example.core.trace.TraceStatus;
import io.devfactory.example.core.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class LogTraceAspect {

  private final LogTrace logTrace;

  public LogTraceAspect(LogTrace logTrace) {
    this.logTrace = logTrace;
  }

  @Around("execution(* io.devfactory.example.core.app..*(..))")
  public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
    TraceStatus status = null;
    Object result;

    try {
      String message = joinPoint.getSignature().toShortString();
      status = logTrace.begin(message);

      // 로직 호출
      result = joinPoint.proceed();

      logTrace.end(status);

    } catch (Exception e) {
      logTrace.exception(status, e);
      throw e;
    }

    return result;
  }

}
