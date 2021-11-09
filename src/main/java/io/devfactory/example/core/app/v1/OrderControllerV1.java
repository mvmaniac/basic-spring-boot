package io.devfactory.example.core.app.v1;

import io.devfactory.example.core.trace.TraceStatus;
import io.devfactory.example.core.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderControllerV1 {

  private final HelloTraceV1 trace;
  private final OrderServiceV1 orderService;

  @SuppressWarnings("DuplicatedCode")
  @GetMapping("/v1/request")
  public ResponseEntity<String> request(String itemId) {
    TraceStatus status = null;

    try {
      status = trace.begin("OrderController.request()");
      orderService.orderItem(itemId);
      trace.end(status);

      return ResponseEntity.ok("ok");

    } catch (Exception e) {
      trace.exception(status, e);
      throw e;
    }
  }

}
