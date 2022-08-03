package io.devfactory.example.tx.apply;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class TxBasicTest {

  @Autowired
  private BasicService basicService;

  @DisplayName("트랜잭션 AOP 적용 확인")
  @Test
  void proxyCheck() {
    log.info("aop class={}", basicService.getClass());
    assertThat(AopUtils.isAopProxy(basicService)).isTrue();
  }

  @DisplayName("tx 테스트")
  @Test
  void txTest() {
    basicService.tx();
    basicService.nonTx();
  }

  @TestConfiguration
  static class TxApplyBasicConfig {

    @Bean
    public BasicService basicService() {
      return new BasicService();
    }

  }

  @Slf4j
  static class BasicService {

    @Transactional
    public void tx() {
      log.info("call tx");
      final var isTxActive = TransactionSynchronizationManager.isActualTransactionActive();
      log.info("tx active={}", isTxActive);
    }

    public void nonTx() {
      log.info("call nonTx");
      final var isTxActive = TransactionSynchronizationManager.isActualTransactionActive();
      log.info("tx active={}", isTxActive);
    }

  }

}
