package io.devfactory.example.core.pureproxy.concreteproxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy extends ConcreteLogic {

  private final ConcreteLogic concreteLogic;

  public TimeProxy(ConcreteLogic concreteLogic) {
    this.concreteLogic = concreteLogic;
  }

  @Override
  public String operation() {
    log.info("TimeProxy 실행");

    long startTime = System.currentTimeMillis();
    String result = concreteLogic.operation();
    long endTime = System.currentTimeMillis();

    log.info("TimeProxy 종료 resultTime={}ms", endTime - startTime);
    return result;
  }

}
