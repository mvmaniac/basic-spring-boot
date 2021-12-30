package io.devfactory.example.core.app.v3;

import io.devfactory.example.core.trace.TraceStatus;
import io.devfactory.example.core.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryV3 {

  private final LogTrace trace;

  @SuppressWarnings("DuplicatedCode")
  public void save(String itemId) {
    TraceStatus status = null;

    try {
      status = trace.begin("OrderRepository.save()");

      // 저장 로직
      if ("ex".equals(itemId)) {
        throw new IllegalStateException("예외 발생");
      }
      sleep(1000L);

      trace.end(status);

    } catch (Exception e) {
      trace.exception(status, e);
      throw e;
    }
  }

  private void sleep(long millis) {
    try {
      TimeUnit.MILLISECONDS.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

}
