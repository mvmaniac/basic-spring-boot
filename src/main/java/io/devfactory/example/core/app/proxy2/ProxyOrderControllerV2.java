package io.devfactory.example.core.app.proxy2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping
@ResponseBody
public class ProxyOrderControllerV2 {

  private final ProxyOrderServiceV2 orderService;

  public ProxyOrderControllerV2(ProxyOrderServiceV2 proxyOrderServiceV2) {
    this.orderService = proxyOrderServiceV2;
  }

  @GetMapping("/proxy/v2/request")
  public ResponseEntity<String> request(String itemId) {
    orderService.orderItem(itemId);
    return ResponseEntity.ok("ok");
  }

  @GetMapping("/proxy/v2/no-log")
  public ResponseEntity<String> noLog() {
    return ResponseEntity.ok("ok");
  }

}
