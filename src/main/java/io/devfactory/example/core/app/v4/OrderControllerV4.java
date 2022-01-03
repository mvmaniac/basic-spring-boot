package io.devfactory.example.core.app.v4;

import io.devfactory.example.core.trace.logtrace.LogTrace;
import io.devfactory.example.core.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderControllerV4 {

  private final LogTrace trace;
  private final OrderServiceV4 orderService;

  @GetMapping("/v4/request")
  public ResponseEntity<String> request(String itemId) {
    AbstractTemplate<String> template = new AbstractTemplate<>(trace) {
      @Override
      protected String call() {
        orderService.orderItem(itemId);
        return "ok";
      }
    };

    return ResponseEntity.ok(template.execute("OrderController.request()"));
  }

}
