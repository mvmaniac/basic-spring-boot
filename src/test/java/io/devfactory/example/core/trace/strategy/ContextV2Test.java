package io.devfactory.example.core.trace.strategy;

import io.devfactory.example.core.trace.strategy.code.strategy.ContextV2;
import io.devfactory.example.core.trace.strategy.code.strategy.Strategy;
import io.devfactory.example.core.trace.strategy.code.strategy.StrategyLogic1;
import io.devfactory.example.core.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("squid:S1186")
@Slf4j
class ContextV2Test {

  @DisplayName("전략 패턴 적용 후 테스트")
  @Test
  void strategyV1() {
    ContextV2 context = new ContextV2();

    context.execute(new StrategyLogic1());
    context.execute(new StrategyLogic2());
  }

  @SuppressWarnings("Convert2Lambda")
  @DisplayName("전략 패턴 적용 후 테스트 (익명 내부 클래스 활용)")
  @Test
  void strategyV2() {
    ContextV2 context = new ContextV2();

    context.execute(new Strategy() {
      @Override
      public void call() {
        log.info("비즈니스 로직1 실행");
      }
    });
    context.execute(new Strategy() {
      @Override
      public void call() {
        log.info("비즈니스 로직2 실행");
      }
    });
  }

  @DisplayName("전략 패턴 적용 후 테스트 (람다 사용)")
  @Test
  void strategyV4() {
    ContextV2 context = new ContextV2();

    context.execute(() -> log.info("비즈니스 로직1 실행"));
    context.execute(() -> log.info("비즈니스 로직2 실행"));
  }

}
