package io.devfactory.example.core.app.v4;

import io.devfactory.example.core.trace.logtrace.LogTrace;
import io.devfactory.example.core.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@SuppressWarnings("ClassCanBeRecord")
@RequiredArgsConstructor
@Service
public class OrderServiceV4 {

  private final LogTrace trace;
  private final OrderRepositoryV4 orderRepository;

  public void orderItem(String itemId) {
    AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
      @Override
      protected Void call() {
        orderRepository.save(itemId);
        return null;
      }
    };
    template.execute("OrderService.orderItem()");
  }

}
