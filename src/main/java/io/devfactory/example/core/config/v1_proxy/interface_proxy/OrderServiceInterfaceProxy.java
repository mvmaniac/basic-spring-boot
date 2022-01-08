package io.devfactory.example.core.config.v1_proxy.interface_proxy;

import io.devfactory.example.core.app.proxy1.ProxyOrderServiceV1;
import io.devfactory.example.core.trace.TraceStatus;
import io.devfactory.example.core.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("ClassCanBeRecord")
@RequiredArgsConstructor
public class OrderServiceInterfaceProxy implements ProxyOrderServiceV1 {
  
  private final ProxyOrderServiceV1 target;
  private final LogTrace trace;

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
