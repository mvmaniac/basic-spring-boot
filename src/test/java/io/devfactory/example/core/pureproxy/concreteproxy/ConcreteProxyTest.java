package io.devfactory.example.core.pureproxy.concreteproxy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("squid:S2699")
@Slf4j
class ConcreteProxyTest {

  @DisplayName("프록시 적용 전 테스트")
  @Test
  void noProxy() {
    ConcreteLogic concreteLogic = new ConcreteLogic();
    ConcreteClient client = new ConcreteClient(concreteLogic);

    client.execute();
  }

  @DisplayName("프록시 적용 후 테스트")
  @Test
  void addProxy() {
    ConcreteLogic concreteLogic = new ConcreteLogic();
    TimeProxy timeProxy = new TimeProxy(concreteLogic);
    ConcreteClient client = new ConcreteClient(timeProxy);

    client.execute();
  }

}
