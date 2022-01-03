package io.devfactory.example.core.trace.template;

import io.devfactory.example.core.trace.template.code.AbstractTemplate;
import io.devfactory.example.core.trace.template.code.SubClassLogic1;
import io.devfactory.example.core.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("squid:S2699")
@Slf4j
class TemplateMethodTest {

  @DisplayName("템플릿 메서드 패턴 적용 전 테스트")
  @Test
  void templateMethodV0() {
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

  @DisplayName("템플릿 메서드 패턴 적용 후 테스트")
  @Test
  void templateMethodV1() {
    AbstractTemplate template1 = new SubClassLogic1();
    template1.execute();

    AbstractTemplate template2 = new SubClassLogic2();
    template2.execute();
  }

  @DisplayName("템플릿 메서드 패턴 적용 후 테스트 (익명 내부 클래스 활용)")
  @Test
  void templateMethodV2() {
    AbstractTemplate template1 = new AbstractTemplate() {
      @Override
      protected void call() {
        log.info("비즈니스 로직1 실행...");
      }
    };
    log.info("templateLogic1={}", template1.getClass());
    template1.execute();

    AbstractTemplate template2 = new AbstractTemplate() {
      @Override
      protected void call() {
        log.info("비즈니스 로직2 실행...");
      }
    };
    log.info("templateLogic2={}", template2.getClass());
    template2.execute();
  }

}
