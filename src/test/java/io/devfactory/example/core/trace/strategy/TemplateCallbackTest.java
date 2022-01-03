package io.devfactory.example.core.trace.strategy;

import io.devfactory.example.core.trace.strategy.code.template.Callback;
import io.devfactory.example.core.trace.strategy.code.template.TimeLogTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("squid:S2699")
@Slf4j
class TemplateCallbackTest {

  @SuppressWarnings("Convert2Lambda")
  @DisplayName("템플릿 콜백 패턴 적용 후 테스트 (익명 내부 클래스 활용)")
  @Test
  void strategyV1() {
    TimeLogTemplate timeLogTemplate = new TimeLogTemplate();

    timeLogTemplate.execute(new Callback() {
      @Override
      public void call() {
        log.info("비즈니스 로직1 실행");
      }
    });
    timeLogTemplate.execute(new Callback() {
      @Override
      public void call() {
        log.info("비즈니스 로직2 실행");
      }
    });
  }
  
  @DisplayName("전략 패턴 적용 후 테스트 (람다 사용)")
  @Test
  void strategyV2() {
    TimeLogTemplate timeLogTemplate = new TimeLogTemplate();

    timeLogTemplate.execute(() -> log.info("비즈니스 로직1 실행"));
    timeLogTemplate.execute(() -> log.info("비즈니스 로직2 실행"));
  }

}
