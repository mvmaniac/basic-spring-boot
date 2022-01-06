package io.devfactory.example.core.app.proxy3;

import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class ProxyOrderRepositoryV3 {

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
