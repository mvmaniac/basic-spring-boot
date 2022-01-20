package io.devfactory.example.core.app.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

@SuppressWarnings({"DuplicatedCode", "squid:S1118"})
@Slf4j
public class AspectV6Advice {

  @Order(2)
  @Aspect
  public static class LogAspect {

    @Around("io.devfactory.example.core.app.aspect.PointCuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
      log.info("[aspectV6 - log] {}", joinPoint.getSignature());
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
        // @Before
        log.info("[트랜잭션 시작] {}", joinPoint.getSignature());

        Object result = joinPoint.proceed();

        // @AfterReturning
        log.info("[트랜잭션 종료] {}", joinPoint.getSignature());

        return result;

      } catch (Exception e) {
        // @AfterThrowing
        log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
        throw e;

      } finally {
        // @After
        log.info("[리소르 릴리즈] {}", joinPoint.getSignature());
      }
    }

  }

  @Order(3)
  @Aspect
  public static class AdviceAspect {
    @Before("io.devfactory.example.core.app.aspect.PointCuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
      log.info("[before] {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "io.devfactory.example.core.app.aspect.PointCuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
      log.info("[return] {} result={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "io.devfactory.example.core.app.aspect.PointCuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
      log.info("[throwing] {} ex={}", joinPoint.getSignature(), ex);
    }

    @AfterThrowing("io.devfactory.example.core.app.aspect.PointCuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
      log.info("[after] {}", joinPoint.getSignature());
    }
  }

}
