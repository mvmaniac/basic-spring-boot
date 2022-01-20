package io.devfactory.example.core.app.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

  // pointcut 시그니처
  @Pointcut("execution(* io.devfactory.example.core.app.order..*(..))")
  private void allOrder() {
    // nothing
  }

  @Around("allOrder()")
  public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("[aspectV2 - log] {}", joinPoint.getSignature());
    return joinPoint.proceed();
  }

}
