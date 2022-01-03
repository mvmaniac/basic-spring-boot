package io.devfactory.example.core.app.v5;

import io.devfactory.example.core.trace.callback.TraceTemplate;
import io.devfactory.example.core.trace.logtrace.LogTrace;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

  private final TraceTemplate template;
  private final OrderRepositoryV5 orderRepository;

  public OrderServiceV5(LogTrace trace, OrderRepositoryV5 orderRepository) {
    this.template = new TraceTemplate(trace);
    this.orderRepository = orderRepository;
  }

  public void orderItem(String itemId) {
    template.execute("OrderService.orderItem()", () -> {
      orderRepository.save(itemId);
      return null;
    });
  }

}
