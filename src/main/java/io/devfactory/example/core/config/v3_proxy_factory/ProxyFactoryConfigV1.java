package io.devfactory.example.core.config.v3_proxy_factory;

import io.devfactory.example.core.app.proxy1.*;
import io.devfactory.example.core.config.v3_proxy_factory.advice.LogTraceAdvice;
import io.devfactory.example.core.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// BeanPostProcessor를 통한 Proxy1Config를 사용하므로 여기는 주석 처리함...
@Slf4j
// @Configuration
public class ProxyFactoryConfigV1 {

  // @Bean
  public ProxyOrderControllerV1 orderControllerProxyFactoryV1(LogTrace logTrace) {
    ProxyOrderControllerV1 controllerImpl = new ProxyOrderControllerV1Impl(orderServiceProxyFactoryV1(logTrace));

    ProxyFactory factory = new ProxyFactory(controllerImpl);
    factory.addAdvisor(getAdvisor(logTrace));

    ProxyOrderControllerV1 proxy = (ProxyOrderControllerV1) factory.getProxy();
    log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), controllerImpl.getClass());
    return proxy;
  }

  // @Bean
  public ProxyOrderServiceV1 orderServiceProxyFactoryV1(LogTrace logTrace) {
    ProxyOrderServiceV1 serviceImpl = new ProxyOrderServiceV1Impl(orderRepositoryProxyFactoryV1(logTrace));

    ProxyFactory factory = new ProxyFactory(serviceImpl);
    factory.addAdvisor(getAdvisor(logTrace));

    ProxyOrderServiceV1 proxy = (ProxyOrderServiceV1) factory.getProxy();
    log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), serviceImpl.getClass());
    return proxy;
  }

  // @Bean
  public ProxyOrderRepositoryV1 orderRepositoryProxyFactoryV1(LogTrace logTrace) {
    ProxyOrderRepositoryV1 repositoryImpl = new ProxyOrderRepositoryV1Impl();

    ProxyFactory factory = new ProxyFactory(repositoryImpl);
    factory.addAdvisor(getAdvisor(logTrace));

    ProxyOrderRepositoryV1 proxy = (ProxyOrderRepositoryV1) factory.getProxy();
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
