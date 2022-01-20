package io.devfactory.example.core.app.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@SuppressWarnings("ClassCanBeRecord")
@Slf4j
@Service
public class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public void orderItem(String itemId) {
    log.info("OrderService 실행...");
    orderRepository.save(itemId);
  }

}