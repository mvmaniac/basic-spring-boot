package io.devfactory.example.core.app.v2;

import io.devfactory.example.core.trace.TraceId;
import io.devfactory.example.core.trace.TraceStatus;
import io.devfactory.example.core.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@SuppressWarnings("ClassCanBeRecord")
@RequiredArgsConstructor
@Service
public class OrderServiceV2 {

  private final HelloTraceV2 trace;
  private final OrderRepositoryV2 orderRepository;

  public void orderItem(TraceId traceId, String itemId) {
    TraceStatus status = null;

    try {
      status = trace.beginSync(traceId, "OrderService.orderItem()");
      orderRepository.save(status.getTraceId(), itemId);
      trace.end(status);

    } catch (Exception e) {
      trace.exception(status, e);
      throw e;
    }
  }

}
