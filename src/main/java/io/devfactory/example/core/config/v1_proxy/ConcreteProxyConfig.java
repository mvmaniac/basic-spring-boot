package io.devfactory.example.core.config.v1_proxy;

import io.devfactory.example.core.app.proxy2.ProxyOrderControllerV2;
import io.devfactory.example.core.app.proxy2.ProxyOrderRepositoryV2;
import io.devfactory.example.core.app.proxy2.ProxyOrderServiceV2;
import io.devfactory.example.core.config.v1_proxy.concrete_proxy.OrderControllerConcreteProxy;
import io.devfactory.example.core.config.v1_proxy.concrete_proxy.OrderRepositoryConcreteProxy;
import io.devfactory.example.core.config.v1_proxy.concrete_proxy.OrderServiceConcreteProxy;
import io.devfactory.example.core.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConcreteProxyConfig {

  @Bean
  public ProxyOrderControllerV2 orderControllerProxyV2(LogTrace logTrace) {
    ProxyOrderControllerV2 controllerImpl = new ProxyOrderControllerV2(orderServiceProxyV2(logTrace));
    return new OrderControllerConcreteProxy(controllerImpl, logTrace);
  }

  @Bean
  public ProxyOrderServiceV2 orderServiceProxyV2(LogTrace logTrace) {
    ProxyOrderServiceV2 serviceImpl = new ProxyOrderServiceV2(orderRepositoryProxyV2(logTrace));
    return new OrderServiceConcreteProxy(serviceImpl, logTrace);
  }

  @Bean
  public ProxyOrderRepositoryV2 orderRepositoryProxyV2(LogTrace logTrace) {
    ProxyOrderRepositoryV2 repositoryImpl = new ProxyOrderRepositoryV2();
    return new OrderRepositoryConcreteProxy(repositoryImpl, logTrace);
  }

}
