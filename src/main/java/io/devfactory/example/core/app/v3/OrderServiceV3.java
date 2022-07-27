package io.devfactory.example.core.app.v3;

import io.devfactory.example.core.trace.TraceStatus;
import io.devfactory.example.core.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceV3 {

  private final LogTrace trace;
  private final OrderRepositoryV3 orderRepository;

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
