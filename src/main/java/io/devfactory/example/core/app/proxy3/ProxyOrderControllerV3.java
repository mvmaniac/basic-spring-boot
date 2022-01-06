package io.devfactory.example.core.app.proxy3;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyOrderControllerV3 {

  private final ProxyOrderServiceV3 orderService;

  public ProxyOrderControllerV3(ProxyOrderServiceV3 proxyOrderServiceV3) {
    this.orderService = proxyOrderServiceV3;
  }

  @GetMapping("/proxy/v3/request")
  public ResponseEntity<String> request(String itemId) {
    orderService.orderItem(itemId);
    return ResponseEntity.ok("ok");
  }

  @GetMapping("/proxy/v3/no-log")
  public ResponseEntity<String> noLog() {
    return ResponseEntity.ok("ok");
  }

}
