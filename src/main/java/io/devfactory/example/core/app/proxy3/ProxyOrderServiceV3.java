package io.devfactory.example.core.app.proxy3;

import org.springframework.stereotype.Service;

@Service
public class ProxyOrderServiceV3 {

  private final ProxyOrderRepositoryV3 orderRepository;

  public ProxyOrderServiceV3(ProxyOrderRepositoryV3 proxyOrderRepositoryV3) {
    this.orderRepository = proxyOrderRepositoryV3;
  }

  public void orderItem(String itemId) {
    orderRepository.save(itemId);
  }

}
