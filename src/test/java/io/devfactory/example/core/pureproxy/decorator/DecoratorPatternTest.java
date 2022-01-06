package io.devfactory.example.core.pureproxy.decorator;

import io.devfactory.example.core.pureproxy.decorator.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("squid:S2699")
@Slf4j
class DecoratorPatternTest {

  @DisplayName("데코레이터 패턴 적용 전 테스트")
  @Test
  void noDecorator() {
    Component component = new RealComponent();
    DecoratorPatternClient client = new DecoratorPatternClient(component);

    client.execute();
  }

  @DisplayName("데코레이터 패턴 적용 후 테스트")
  @Test
  void decorator1() {
    Component component = new RealComponent();
    Component messageDecorator = new MessageDecorator(component);
    DecoratorPatternClient client = new DecoratorPatternClient(messageDecorator);

    client.execute();
  }

  @DisplayName("데코레이터 패턴 적용 후 테스트 (체인 적용)")
  @Test
  void decorator2() {
    Component component = new RealComponent();
    Component messageDecorator = new MessageDecorator(component);
    Component timeDecorator = new TimeDecorator(messageDecorator);
    DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);

    client.execute();
  }

}
