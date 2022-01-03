package io.devfactory.example.core.trace.strategy;

import io.devfactory.example.core.trace.strategy.code.strategy.ContextV1;
import io.devfactory.example.core.trace.strategy.code.strategy.Strategy;
import io.devfactory.example.core.trace.strategy.code.strategy.StrategyLogic1;
import io.devfactory.example.core.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("squid:S2699")
@Slf4j
class ContextV1Test {

  @DisplayName("전략 패턴 적용 전 테스트")
  @Test
  void strategyV0() {
    logic1();
    logic2();
  }

  private void logic1() {
    long startTime = System.currentTimeMillis();

    // 비즈니스 로직 실행
    log.info("비즈니스 로직1 실행...");
    // 비즈니스 로직 종료

    long endTime = System.currentTimeMillis();
    long resultTime = endTime - startTime;

    log.info("resultTime = {}", resultTime);
  }

  private void logic2() {
    long startTime = System.currentTimeMillis();

    // 비즈니스 로직 실행
    log.info("비즈니스 로직2 실행...");
    // 비즈니스 로직 종료

    long endTime = System.currentTimeMillis();
    long resultTime = endTime - startTime;

    log.info("resultTime = {}", resultTime);
  }

  @DisplayName("전략 패턴 적용 후 테스트")
  @Test
  void strategyV1() {
    Strategy strategyLogic1 = new StrategyLogic1();
    ContextV1 context1 = new ContextV1(strategyLogic1);
    context1.execute();

    Strategy strategyLogic2 = new StrategyLogic2();
    ContextV1 context2 = new ContextV1(strategyLogic2);
    context2.execute();
  }

  @SuppressWarnings("Convert2Lambda")
  @DisplayName("전략 패턴 적용 후 테스트 (익명 내부 클래스 활용)")
  @Test
  void strategyV2() {
    Strategy strategyLogic1 = new Strategy() {
      @Override
      public void call() {
        log.info("비즈니스 로직1 실행");
      }
    };
    ContextV1 context1 = new ContextV1(strategyLogic1);
    log.info("strategyLogic1={}", strategyLogic1.getClass());
    context1.execute();

    Strategy strategyLogic2 = new Strategy() {
      @Override
      public void call() {
        log.info("비즈니스 로직2 실행");
      }
    };
    ContextV1 context2 = new ContextV1(strategyLogic2);
    log.info("strategyLogic2={}", strategyLogic2.getClass());
    context2.execute();
  }

  @SuppressWarnings("Convert2Lambda")
  @DisplayName("전략 패턴 적용 후 테스트 (생성자에 전달)")
  @Test
  void strategyV3() {
    ContextV1 context1 = new ContextV1(new Strategy() {
      @Override
      public void call() {
        log.info("비즈니스 로직1 실행");
      }
    });
    context1.execute();

    ContextV1 context2 = new ContextV1(new Strategy() {
      @Override
      public void call() {
        log.info("비즈니스 로직2 실행");
      }
    });
    context2.execute();
  }
  
  @DisplayName("전략 패턴 적용 후 테스트 (람다 사용)")
  @Test
  void strategyV4() {
    ContextV1 context1 = new ContextV1(() -> log.info("비즈니스 로직1 실행"));
    context1.execute();

    ContextV1 context2 = new ContextV1(() -> log.info("비즈니스 로직2 실행"));
    context2.execute();
  }

}
