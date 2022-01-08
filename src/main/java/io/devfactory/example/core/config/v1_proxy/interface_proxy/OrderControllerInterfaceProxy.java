package io.devfactory.example.core.config.v1_proxy.interface_proxy;

import io.devfactory.example.core.app.proxy1.ProxyOrderControllerV1;
import io.devfactory.example.core.trace.TraceStatus;
import io.devfactory.example.core.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@SuppressWarnings({"ClassCanBeRecord", "DuplicatedCode"})
@RequiredArgsConstructor
public class OrderControllerInterfaceProxy implements ProxyOrderControllerV1 {

  private final ProxyOrderControllerV1 target;
  private final LogTrace trace;

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
