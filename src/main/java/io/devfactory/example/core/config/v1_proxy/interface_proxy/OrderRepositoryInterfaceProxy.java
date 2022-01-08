package io.devfactory.example.core.config.v1_proxy.interface_proxy;

import io.devfactory.example.core.app.proxy1.ProxyOrderRepositoryV1;
import io.devfactory.example.core.trace.TraceStatus;
import io.devfactory.example.core.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("ClassCanBeRecord")
@RequiredArgsConstructor
public class OrderRepositoryInterfaceProxy implements ProxyOrderRepositoryV1 {

  private final ProxyOrderRepositoryV1 target;
  private final LogTrace trace;

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
