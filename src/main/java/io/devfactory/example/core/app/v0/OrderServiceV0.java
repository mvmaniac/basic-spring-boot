package io.devfactory.example.core.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@SuppressWarnings("ClassCanBeRecord")
@RequiredArgsConstructor
@Service
public class OrderServiceV0 {

  private final OrderRepositoryV0 orderRepository;

  public void orderItem(String itemId) {
    orderRepository.save(itemId);
  }

}