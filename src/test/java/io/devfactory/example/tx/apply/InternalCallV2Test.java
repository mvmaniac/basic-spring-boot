package io.devfactory.example.tx.apply;


import io.devfactory.example.core.app.internalcall.InternalService;
import lombok.RequiredArgsConstructor;
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
class InternalCallV2Test {

  @Autowired
  private CallService callService;

  @DisplayName("printProxy")
  @Test
  void printProxy() {
    log.info("callService class={}", callService.getClass());
  }

  @DisplayName("externalCallV2")
  @Test
  void externalCallV2() {
    // InternalService 클래스로 별도로 분리했기 때문에
    // transaction 적용 됨 (tx active=false)
    callService.external();
  }

  @TestConfiguration
  static class InternalCallV2TestConfig {

    @Bean
    public CallService callService() {
      // 아래처럼 직접 생성해서 주입 하는 경우 안됨
      // new CallService(new InternalServiceV2());
      return new CallService(internalServiceV2());
    }

    @Bean
    public InternalServiceV2 internalServiceV2() {
      return new InternalServiceV2();
    }

  }

  @RequiredArgsConstructor
  static class CallService {

    private final InternalServiceV2 internalServiceV2;

    public void external() {
      log.info("call external...");
      printTxInfo();
      internalServiceV2.internal();
    }

    private void printTxInfo() {
      final var isTxActive = TransactionSynchronizationManager.isActualTransactionActive();
      log.info("tx active={}", isTxActive);
    }

  }

  static class InternalServiceV2 {

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
