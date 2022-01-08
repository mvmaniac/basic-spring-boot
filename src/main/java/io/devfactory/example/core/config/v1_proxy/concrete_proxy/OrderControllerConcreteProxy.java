package io.devfactory.example.core.config.v1_proxy.concrete_proxy;

import io.devfactory.example.core.app.proxy2.ProxyOrderControllerV2;
import io.devfactory.example.core.trace.TraceStatus;
import io.devfactory.example.core.trace.logtrace.LogTrace;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("DuplicatedCode")
public class OrderControllerConcreteProxy extends ProxyOrderControllerV2 {

  private final ProxyOrderControllerV2 target;
  private final LogTrace trace;

  public OrderControllerConcreteProxy(ProxyOrderControllerV2 target, LogTrace trace) {
    super(null); // 부모 기능을 쓰지 않고 프록시 역할만 하기 떄문에 null로 셋팅
    this.target = target;
    this.trace = trace;
  }

  @Override
  public ResponseEntity<String> request(String itemId) {
    TraceStatus status = null;

    try {
      status = trace.begin("OrderController.request()");

      // target 호출
      ResponseEntity<String> result = target.request(itemId);

      trace.end(status);
      return result;

    } catch (Exception e) {
      trace.exception(status, e);
      throw e;
    }
  }

  @Override
  public ResponseEntity<String> noLog() {
    return target.noLog();
  }

}
