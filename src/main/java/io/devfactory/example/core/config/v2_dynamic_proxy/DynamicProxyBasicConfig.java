package io.devfactory.example.core.config.v2_dynamic_proxy;

import io.devfactory.example.core.app.proxy1.*;
import io.devfactory.example.core.config.v2_dynamic_proxy.handler.LogTraceBasicHandler;
import io.devfactory.example.core.trace.logtrace.LogTrace;

import java.lang.reflect.Proxy;

// DynamicProxyFilterConfig에서 사용하므로 여기는 주석 처리함...
// @Configuration
public class DynamicProxyBasicConfig {

  // @Bean
  public ProxyOrderControllerV1 orderControllerDynamicProxyV1(LogTrace logTrace) {
    ProxyOrderControllerV1 controllerImpl = new ProxyOrderControllerV1Impl(orderServiceDynamicProxyV1(logTrace));
    return (ProxyOrderControllerV1) Proxy.newProxyInstance(ProxyOrderControllerV1.class.getClassLoader(),
        new Class[]{ProxyOrderControllerV1.class},
        new LogTraceBasicHandler(controllerImpl, logTrace));
  }

  // @Bean
  public ProxyOrderServiceV1 orderServiceDynamicProxyV1(LogTrace logTrace) {
    ProxyOrderServiceV1 serviceImpl = new ProxyOrderServiceV1Impl(orderRepositoryDynamicProxyV1(logTrace));
    return (ProxyOrderServiceV1) Proxy.newProxyInstance(ProxyOrderServiceV1.class.getClassLoader(),
        new Class[]{ProxyOrderServiceV1.class},
        new LogTraceBasicHandler(serviceImpl, logTrace));
  }

  // @Bean
  public ProxyOrderRepositoryV1 orderRepositoryDynamicProxyV1(LogTrace logTrace) {
    ProxyOrderRepositoryV1 repositoryImpl = new ProxyOrderRepositoryV1Impl();
    return (ProxyOrderRepositoryV1) Proxy.newProxyInstance(ProxyOrderRepositoryV1.class.getClassLoader(),
        new Class[]{ProxyOrderRepositoryV1.class},
        new LogTraceBasicHandler(repositoryImpl, logTrace));
  }

}
