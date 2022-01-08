package io.devfactory.example.core.config.v1_proxy.concrete_proxy;

import io.devfactory.example.core.app.proxy2.ProxyOrderRepositoryV2;
import io.devfactory.example.core.trace.TraceStatus;
import io.devfactory.example.core.trace.logtrace.LogTrace;

public class OrderRepositoryConcreteProxy extends ProxyOrderRepositoryV2 {

  private final ProxyOrderRepositoryV2 target;
  private final LogTrace trace;

  public OrderRepositoryConcreteProxy(ProxyOrderRepositoryV2 target, LogTrace trace) {
    this.target = target;
    this.trace = trace;
  }

  @Override
  public void save(String itemId) {
    TraceStatus status = null;

    try {
      status = trace.begin("OrderRepository.save()");

      // target 호출
      target.save(itemId);

      trace.end(status);

    } catch (Exception e) {
      trace.exception(status, e);
      throw e;
    }
  }

}
