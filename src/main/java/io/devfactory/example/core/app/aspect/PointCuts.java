package io.devfactory.example.core.app.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

  // order 패키지와 하위 패키지
  @Pointcut("execution(* io.devfactory.example.core.app.order..*(..))")
  public void allOrder() {
    // nothing
  }

  // 클래스 이름 피턴이 *Service
  @Pointcut("execution(* io.devfactory.example.core.app.order.*Service.*(..))")
  public void allService() {
    // nothing
  }

  // allOrder && allService
  @Pointcut("allOrder() && allService()")
  public void orderAndService() {
    // nothing
  }

}
