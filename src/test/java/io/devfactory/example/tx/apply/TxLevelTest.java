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
class TxLevelTest {

  @Autowired
  private LevelService levelService;

  @DisplayName("tx 테스트")
  @Test
  void orderTest() {
    levelService.write();
    levelService.read();
  }

  @TestConfiguration
  static class TxLevelTestConfig {

    @Bean
    public LevelService levelService() {
      return new LevelService();
    }

  }

  @Slf4j
  @Transactional(readOnly = true)
  static class LevelService {

    @Transactional
    public void write() {
      log.info("call write");
      printTxInfo();
    }

    public void read() {
      log.info("call read");
      printTxInfo();
    }

    private void printTxInfo() {
      final var isTxActive = TransactionSynchronizationManager.isActualTransactionActive();
      log.info("tx active={}", isTxActive);
      final var isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
      log.info("tx readOnly={}", isReadOnly);
    }

  }

}
