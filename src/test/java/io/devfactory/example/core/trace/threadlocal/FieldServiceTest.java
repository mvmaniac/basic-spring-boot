package io.devfactory.example.core.trace.threadlocal;

import io.devfactory.example.core.trace.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("squid:S1186")
@Slf4j
class FieldServiceTest {

  private final FieldService fieldService = new FieldService();

  @DisplayName("테스트")
  @Test
  void fieldServiceTest() {
    log.info("main start");

    Runnable userA = () -> fieldService.logic("userA");
    Runnable userB = () -> fieldService.logic("userB");

    Thread threadA = new Thread(userA);
    threadA.setName("thread-A");

    Thread threadB = new Thread(userB);
    threadB.setName("thread-B");

    threadA.start();
    // sleep(2000); // 동시성 문제 발생X
    sleep(100); // 동시성 문제 발생O
    threadB.start();

    sleep(3000); // 메인 쓰레드 종료 대기
    log.info("main exit");
  }

  private void sleep(long millis) {
    try {
      TimeUnit.MILLISECONDS.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

}
