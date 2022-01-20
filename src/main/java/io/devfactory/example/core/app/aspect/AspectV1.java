package io.devfactory.example.core.app.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV1 {

  @Around("execution(* io.devfactory.example.core.app.order..*(..))")
  public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("[aspectV1 - log] {}", joinPoint.getSignature()); // join point 시그니처
    return joinPoint.proceed();
  }

}
