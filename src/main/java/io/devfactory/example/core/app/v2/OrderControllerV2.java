package io.devfactory.example.core.app.v2;

import io.devfactory.example.core.trace.TraceStatus;
import io.devfactory.example.core.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderControllerV2 {

  private final HelloTraceV2 trace;
  private final OrderServiceV2 orderService;

  @GetMapping("/v2/request")
  public ResponseEntity<String> request(String itemId) {
    TraceStatus status = null;

    try {
      status = trace.begin("OrderController.request()");
      orderService.orderItem(status.getTraceId(), itemId);
      trace.end(status);

      return ResponseEntity.ok("ok");

    } catch (Exception e) {
      trace.exception(status, e);
      throw e;
    }
  }

}
