package io.devfactory.example.core.app.proxy2;

import java.util.concurrent.TimeUnit;

public class ProxyOrderRepositoryV2 {

  public void save(String itemId) {
    // 저장 로직
    if ("ex".equals(itemId)) {
      throw new IllegalStateException("예외 발생");
    }

    sleep(1000L);
  }

  private void sleep(long millis) {
    try {
      TimeUnit.MILLISECONDS.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

}
