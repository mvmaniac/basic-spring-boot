package io.devfactory.example.core.config;

import io.devfactory.example.core.app.proxy2.ProxyOrderControllerV2;
import io.devfactory.example.core.app.proxy2.ProxyOrderRepositoryV2;
import io.devfactory.example.core.app.proxy2.ProxyOrderServiceV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// ConcreteProxyConfig에서 사용하므로 여기는 주석 처리함...
// @Configuration
public class Proxy2Config {

  // @Bean
  public ProxyOrderControllerV2 proxyOrderControllerV2() {
    return new ProxyOrderControllerV2(proxyOrderServiceV2());
  }

  // @Bean
  public ProxyOrderServiceV2 proxyOrderServiceV2() {
    return new ProxyOrderServiceV2(proxyOrderRepositoryV2());
  }

  // @Bean
  public ProxyOrderRepositoryV2 proxyOrderRepositoryV2() {
    return new ProxyOrderRepositoryV2();
  }

}
