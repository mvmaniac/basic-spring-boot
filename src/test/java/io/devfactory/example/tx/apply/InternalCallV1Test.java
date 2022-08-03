package io.devfactory.example.tx.apply;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
class InternalCallV1Test {

  @Autowired
  private CallService callService;

  @DisplayName("printProxy")
  @Test
  void printProxy() {
    log.info("callService class={}", callService.getClass());
  }

  @DisplayName("internalCall")
  @Test
  void internalCall() {
    callService.internal(); // transaction 적용 됨 (tx active=true)
  }

  @DisplayName("externalCall")
  @Test
  void externalCall() {
    callService.external(); // transaction 적용 안됨 (tx active=false)
  }

  @TestConfiguration
  static class InternalCallV1TestConfig {

    @Bean
    public CallService callService() {
      return new CallService();
    }

  }

  static class CallService {

    public void external() {
      log.info("call external...");
      printTxInfo();
      internal();
    }

    @Transactional
    public void internal() {
      log.info("call internal...");
      printTxInfo();
    }

    private void printTxInfo() {
      final var isTxActive = TransactionSynchronizationManager.isActualTransactionActive();
      log.info("tx active={}", isTxActive);
    }

  }

}
