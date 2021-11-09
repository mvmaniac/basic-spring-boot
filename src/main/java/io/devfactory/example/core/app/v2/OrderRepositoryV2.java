package io.devfactory.example.core.app.v2;

import io.devfactory.example.core.trace.TraceId;
import io.devfactory.example.core.trace.TraceStatus;
import io.devfactory.example.core.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryV2 {

  private final HelloTraceV2 trace;

  public void save(TraceId traceId, String itemId) {
    TraceStatus status = null;

    try {
      status = trace.beginSync(traceId, "OrderRepository.save()");

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
