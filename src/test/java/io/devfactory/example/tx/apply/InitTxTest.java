package io.devfactory.example.tx.apply;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;

@SuppressWarnings("squid:S2699")
@Slf4j
@SpringBootTest
class InitTxTest {

  @Autowired
  private Hello hello;

  @DisplayName("go")
  @Test
  void go() {
    // 초기화 코드는 스프링이 초기화 시점에 호출
    // @PostConstruct와 같이 쓰면 적용 안됨
  }

  @TestConfiguration
  static class InitTxTestConfig {

    @Bean
    public Hello hello() {
      return new Hello();
    }

  }

  static class Hello {

    @Transactional
    @PostConstruct
    public void initV1() {
      final var isTxActive = TransactionSynchronizationManager.isActualTransactionActive();
      log.info("[dev] Hello init @PostConstruct active={}", isTxActive);
    }

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void initV2() {
      final var isTxActive = TransactionSynchronizationManager.isActualTransactionActive();
      log.info("[dev] Hello init ApplicationReadyEvent active={}", isTxActive);
    }

  }

}
