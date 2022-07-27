package io.devfactory.example.core.app.v1;

import io.devfactory.example.core.trace.TraceStatus;
import io.devfactory.example.core.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceV1 {

  private final HelloTraceV1 trace;
  private final OrderRepositoryV1 orderRepository;

  public void orderItem(String itemId) {
    TraceStatus status = null;

    try {
      status = trace.begin("OrderService.orderItem()");
      orderRepository.save(itemId);
      trace.end(status);

    } catch (Exception e) {
      trace.exception(status, e);
      throw e;
    }
  }

}
