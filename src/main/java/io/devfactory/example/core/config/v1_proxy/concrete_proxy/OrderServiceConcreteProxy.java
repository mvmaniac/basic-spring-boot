package io.devfactory.example.core.config.v1_proxy.concrete_proxy;

import io.devfactory.example.core.app.proxy2.ProxyOrderServiceV2;
import io.devfactory.example.core.trace.TraceStatus;
import io.devfactory.example.core.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends ProxyOrderServiceV2 {

  private final ProxyOrderServiceV2 target;
  private final LogTrace trace;

  public OrderServiceConcreteProxy(ProxyOrderServiceV2 target, LogTrace trace) {
    super(null); // 부모 기능을 쓰지 않고 프록시 역할만 하기 떄문에 null로 셋팅
    this.target = target;
    this.trace = trace;
  }

  @Override
  public void orderItem(String itemId) {
    TraceStatus status = null;

    try {
      status = trace.begin("OrderService.orderItem()");

      // target 호출
      target.orderItem(itemId);

      trace.end(status);

    } catch (Exception e) {
      trace.exception(status, e);
      throw e;
    }
  }

}
