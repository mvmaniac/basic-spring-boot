package io.devfactory.example.core.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("ClassCanBeRecord")
@Slf4j
public class DecoratorPatternClient {

  private final Component component;

  public DecoratorPatternClient(Component component) {
    this.component = component;
  }

  public void execute() {
    log.info("result={}", component.operation());
  }

}
