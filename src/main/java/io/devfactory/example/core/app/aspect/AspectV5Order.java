package io.devfactory.example.core.app.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@SuppressWarnings({"DuplicatedCode", "squid:S1118"})
@Slf4j
public class AspectV5Order {

  @Order(2)
  @Aspect
  public static class LogAspect {
    @Around("io.devfactory.example.core.app.aspect.PointCuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
      log.info("[aspectV5 - log] {}", joinPoint.getSignature());
      return joinPoint.proceed();
    }
  }

  @Order(1)
  @Aspect
  public static class TransactionAspect {
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

}
