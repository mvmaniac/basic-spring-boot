package io.devfactory.example.core.config.v3_proxy_factory;

import io.devfactory.example.core.app.proxy2.ProxyOrderControllerV2;
import io.devfactory.example.core.app.proxy2.ProxyOrderRepositoryV2;
import io.devfactory.example.core.app.proxy2.ProxyOrderServiceV2;
import io.devfactory.example.core.config.v3_proxy_factory.advice.LogTraceAdvice;
import io.devfactory.example.core.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// BeanPostProcessor를 통한 Proxy2Config를 사용하므로 여기는 주석 처리함...
@Slf4j
// @Configuration
public class ProxyFactoryConfigV2 {

  // @Bean
  public ProxyOrderControllerV2 orderControllerProxyFactoryV2(LogTrace logTrace) {
    ProxyOrderControllerV2 controllerImpl = new ProxyOrderControllerV2(orderServiceProxyFactoryV2(logTrace));

    ProxyFactory factory = new ProxyFactory(controllerImpl);
    factory.addAdvisor(getAdvisor(logTrace));

    ProxyOrderControllerV2 proxy = (ProxyOrderControllerV2) factory.getProxy();
    log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), controllerImpl.getClass());
    return proxy;
  }

  // @Bean
  public ProxyOrderServiceV2 orderServiceProxyFactoryV2(LogTrace logTrace) {
    ProxyOrderServiceV2 serviceImpl = new ProxyOrderServiceV2(orderRepositoryProxyFactoryV2(logTrace));

    ProxyFactory factory = new ProxyFactory(serviceImpl);
    factory.addAdvisor(getAdvisor(logTrace));

    ProxyOrderServiceV2 proxy = (ProxyOrderServiceV2) factory.getProxy();
    log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), serviceImpl.getClass());
    return proxy;
  }

  // @Bean
  public ProxyOrderRepositoryV2 orderRepositoryProxyFactoryV2(LogTrace logTrace) {
    ProxyOrderRepositoryV2 repositoryImpl = new ProxyOrderRepositoryV2();

    ProxyFactory factory = new ProxyFactory(repositoryImpl);
    factory.addAdvisor(getAdvisor(logTrace));

    ProxyOrderRepositoryV2 proxy = (ProxyOrderRepositoryV2) factory.getProxy();
    log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), repositoryImpl.getClass());
    return proxy;
  }

  private Advisor getAdvisor(LogTrace logTrace) {
    // pointcut
    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    pointcut.setMappedNames("request*", "order*", "save*");

    // advice
    LogTraceAdvice advice = new LogTraceAdvice(logTrace);

    return new DefaultPointcutAdvisor(pointcut, advice);
  }

}
