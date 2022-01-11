package io.devfactory.example.core.jdkdynamic;

import io.devfactory.example.core.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@SuppressWarnings("squid:S2699")
@Slf4j
class JdkDynamicProxyTest {

  @DisplayName("AInterface 테스트")
  @Test
  void dynamicA() {
    AInterface target = new AImpl();

    TimeInvocationHandler handler = new TimeInvocationHandler(target);
    AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(),
        new Class[]{AInterface.class}, handler);

    proxy.call();

    log.info("targetClass={}", target.getClass());
    log.info("proxyClass={}", proxy.getClass());
  }

  @DisplayName("BInterface 테스트")
  @Test
  void dynamicB() {
    BInterface target = new BImpl();

    TimeInvocationHandler handler = new TimeInvocationHandler(target);
    BInterface proxy = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(),
        new Class[]{BInterface.class}, handler);

    proxy.call();

    log.info("targetClass={}", target.getClass());
    log.info("proxyClass={}", proxy.getClass());
  }

}
