package io.devfactory.example.core.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryV0 {

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
