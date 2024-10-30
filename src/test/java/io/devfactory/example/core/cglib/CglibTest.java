package io.devfactory.example.core.cglib;

import io.devfactory.example.core.cglib.code.TimeMethodInterceptor;
import io.devfactory.example.core.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@SuppressWarnings("squid:S1186")
@Slf4j
class CglibTest {

  @DisplayName("CGLIB 테스트")
  @Test
  void cglib() {
    ConcreteService target = new ConcreteService();

    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(ConcreteService.class);
    enhancer.setCallback(new TimeMethodInterceptor(target));

    ConcreteService proxy = (ConcreteService) enhancer.create();

    log.info("targetClass={}", target.getClass());
    log.info("proxyClass={}", proxy.getClass());

    proxy.call();
  }

}
