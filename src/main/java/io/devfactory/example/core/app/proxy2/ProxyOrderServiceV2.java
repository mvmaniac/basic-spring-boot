package io.devfactory.example.core.app.proxy2;

public class ProxyOrderServiceV2 {

  private final ProxyOrderRepositoryV2 orderRepository;

  public ProxyOrderServiceV2(ProxyOrderRepositoryV2 proxyOrderRepositoryV2) {
    this.orderRepository = proxyOrderRepositoryV2;
  }

  public void orderItem(String itemId) {
    orderRepository.save(itemId);
  }

}
