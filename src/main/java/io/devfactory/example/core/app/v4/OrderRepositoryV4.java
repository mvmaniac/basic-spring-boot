package io.devfactory.example.core.app.v4;

import io.devfactory.example.core.trace.logtrace.LogTrace;
import io.devfactory.example.core.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryV4 {

  private final LogTrace trace;

  public void save(String itemId) {
    AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
      @Override
      protected Void call() {
        // 저장 로직
        if ("ex".equals(itemId)) {
          throw new IllegalStateException("예외 발생");
        }
        sleep(1000L);

        return null;
      }
    };
    template.execute("OrderRepository.save()");
  }

  private void sleep(long millis) {
    try {
      TimeUnit.MILLISECONDS.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

}
