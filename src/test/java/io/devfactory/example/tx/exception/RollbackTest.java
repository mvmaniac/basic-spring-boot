package io.devfactory.example.tx.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
class RollbackTest {

  @Autowired
  private RollbackService rollbackService;

  @DisplayName("runtimeException")
  @Test
  void runtimeException() {
    // Rolling back JPA transaction on EntityManager
    assertThatThrownBy(() -> rollbackService.runtimeException())
        .isInstanceOf(RuntimeException.class);
  }

  @DisplayName("checkedException")
  @Test
  void checkedException() {
    // Committing JPA transaction on EntityManager
    assertThatThrownBy(() -> rollbackService.checkedException())
        .isInstanceOf(Exception.class);
  }

  @DisplayName("rollbackFor")
  @Test
  void rollbackFor() {
    // Rolling back JPA transaction on EntityManager
    assertThatThrownBy(() -> rollbackService.rollbackFor())
        .isInstanceOf(Exception.class);
  }

  @TestConfiguration
  static class RollbackTestConfig {

    @Bean
    public RollbackService rollbackService() {
      return new RollbackService();
    }

  }

  static class RollbackService {

    // 런타임 예외 발생: 롤백
    @Transactional
    public void runtimeException() {
      log.info("call runtimeException...");
      throw new RuntimeException();
    }

    // 체크 예외 발생: 커밋
    @Transactional
    public void checkedException() throws Exception {
      log.info("call checkedException...");
      throw new Exception();
    }

    // 체크 예외 rollbackFor 지정 : 롤백
    @Transactional(rollbackFor = Exception.class)
    public void rollbackFor() throws Exception {
      log.info("call checkedException...");
      throw new Exception();
    }

  }


}
