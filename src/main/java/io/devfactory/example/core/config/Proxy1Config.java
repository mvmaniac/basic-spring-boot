package io.devfactory.example.core.config;

import io.devfactory.example.core.app.proxy1.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// BeanPostProcessorConfig에서 사용함
@Configuration
public class Proxy1Config {

  @Bean
  public ProxyOrderControllerV1 proxyOrderControllerV1() {
    return new ProxyOrderControllerV1Impl(proxyOrderServiceV1());
  }

  @Bean
  public ProxyOrderServiceV1 proxyOrderServiceV1() {
    return new ProxyOrderServiceV1Impl(proxyOrderRepositoryV1());
  }

  @Bean
  public ProxyOrderRepositoryV1 proxyOrderRepositoryV1() {
    return new ProxyOrderRepositoryV1Impl();
  }

}
