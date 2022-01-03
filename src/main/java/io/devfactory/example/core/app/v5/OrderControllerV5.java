package io.devfactory.example.core.app.v5;

import io.devfactory.example.core.trace.callback.TraceCallback;
import io.devfactory.example.core.trace.callback.TraceTemplate;
import io.devfactory.example.core.trace.logtrace.LogTrace;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControllerV5 {

  private final TraceTemplate template;
  private final OrderServiceV5 orderService;

  public OrderControllerV5(LogTrace trace, OrderServiceV5 orderService) {
    this.template = new TraceTemplate(trace);
    this.orderService = orderService;
  }

  @GetMapping("/v5/request")
  public ResponseEntity<String> request(String itemId) {
    return ResponseEntity.ok(template.execute("OrderController.request()", new TraceCallback<>() {
      @Override
      public String call() {
        orderService.orderItem(itemId);
        return "ok";
      }
    }));
  }

}
