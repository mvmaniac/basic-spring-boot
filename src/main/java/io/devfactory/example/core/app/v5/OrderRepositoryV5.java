package io.devfactory.example.core.app.v5;

import io.devfactory.example.core.trace.callback.TraceTemplate;
import io.devfactory.example.core.trace.logtrace.LogTrace;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class OrderRepositoryV5 {

  private final TraceTemplate template;

  public OrderRepositoryV5(LogTrace trace) {
    this.template = new TraceTemplate(trace);
  }

  public void save(String itemId) {
    template.execute("OrderRepository.save()", () -> {
      // 저장 로직
      if ("ex".equals(itemId)) {
        throw new IllegalStateException("예외 발생");
      }
      sleep(1000L);
      return null;
    });
  }

  private void sleep(long millis) {
    try {
      TimeUnit.MILLISECONDS.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

}
