package io.devfactory.example.core.app.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@SuppressWarnings("DuplicatedCode")
@Slf4j
@Aspect
public class AspectV4PointCut {

  @Around("io.devfactory.example.core.app.aspect.PointCuts.allOrder()")
  public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("[aspectV4 - log] {}", joinPoint.getSignature());
    return joinPoint.proceed();
  }

  // order 패키지와 하위 패키지이면서 클래스 이름 패턴이 *Service
  @Around("io.devfactory.example.core.app.aspect.PointCuts.orderAndService()")
  public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
    try {
      log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
      Object result = joinPoint.proceed();
      log.info("[트랜잭션 종료] {}", joinPoint.getSignature());

      return result;
      
    } catch (Exception e) {
      log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
      throw e;

    } finally {
      log.info("[리소르 릴리즈] {}", joinPoint.getSignature());
    }
  }

}
