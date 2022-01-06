package io.devfactory.example.core.app.proxy1;

import org.springframework.http.ResponseEntity;

public class ProxyOrderControllerV1Impl implements ProxyOrderControllerV1 {

  private final ProxyOrderServiceV1 orderService;

  public ProxyOrderControllerV1Impl(ProxyOrderServiceV1 proxyOrderServiceV1) {
    this.orderService = proxyOrderServiceV1;
  }

  @Override
  public ResponseEntity<String> request(String itemId) {
    orderService.orderItem(itemId);
    return ResponseEntity.ok("ok");
  }

  @Override
  public ResponseEntity<String> noLog() {
    return ResponseEntity.ok("ok");
  }

}
