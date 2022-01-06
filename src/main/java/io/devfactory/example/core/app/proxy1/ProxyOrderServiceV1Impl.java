package io.devfactory.example.core.app.proxy1;

public class ProxyOrderServiceV1Impl implements ProxyOrderServiceV1 {

  private final ProxyOrderRepositoryV1 orderRepository;

  public ProxyOrderServiceV1Impl(ProxyOrderRepositoryV1 proxyOrderRepositoryV1) {
    this.orderRepository = proxyOrderRepositoryV1;
  }

  @Override
  public void orderItem(String itemId) {
    orderRepository.save(itemId);
  }

}
