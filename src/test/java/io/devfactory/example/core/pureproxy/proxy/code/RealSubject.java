package io.devfactory.example.core.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RealSubject implements Subject {

  @Override
  public String operation() {
    log.info("실제 객체 호출");
    sleep(1000);
    return "operation";
  }

  private void sleep(long millis) {
    try {
      TimeUnit.MILLISECONDS.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

}
