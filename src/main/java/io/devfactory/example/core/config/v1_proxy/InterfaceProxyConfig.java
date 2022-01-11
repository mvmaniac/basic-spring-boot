package io.devfactory.example.core.config.v1_proxy;

import io.devfactory.example.core.app.proxy1.*;
import io.devfactory.example.core.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import io.devfactory.example.core.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import io.devfactory.example.core.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import io.devfactory.example.core.trace.logtrace.LogTrace;

// DynamicProxyBasicConfig에서 사용하므로 여기는 주석 처리함...
// @Configuration
public class InterfaceProxyConfig {

  // @Bean
  public ProxyOrderControllerV1 orderControllerProxyV1(LogTrace logTrace) {
    ProxyOrderControllerV1 controllerImpl = new ProxyOrderControllerV1Impl(orderServiceProxyV1(logTrace));
    return new OrderControllerInterfaceProxy(controllerImpl, logTrace);
  }

  // @Bean
  public ProxyOrderServiceV1 orderServiceProxyV1(LogTrace logTrace) {
    ProxyOrderServiceV1 serviceImpl = new ProxyOrderServiceV1Impl(orderRepositoryProxyV1(logTrace));
    return new OrderServiceInterfaceProxy(serviceImpl, logTrace);
  }

  // @Bean
  public ProxyOrderRepositoryV1 orderRepositoryProxyV1(LogTrace logTrace) {
    ProxyOrderRepositoryV1 repositoryImpl = new ProxyOrderRepositoryV1Impl();
    return new OrderRepositoryInterfaceProxy(repositoryImpl, logTrace);
  }

}
