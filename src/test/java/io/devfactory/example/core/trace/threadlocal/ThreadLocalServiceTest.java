package io.devfactory.example.core.trace.threadlocal;

import io.devfactory.example.core.trace.threadlocal.code.ThreadLocalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("squid:S2699")
@Slf4j
class ThreadLocalServiceTest {

  private final ThreadLocalService threadLocalService = new ThreadLocalService();

  @DisplayName("테스트")
  @Test
  void threadLocalServiceTest() {
    log.info("main start");

    Runnable userA = () -> threadLocalService.logic("userA");
    Runnable userB = () -> threadLocalService.logic("userB");

    Thread threadA = new Thread(userA);
    threadA.setName("thread-A");

    Thread threadB = new Thread(userB);
    threadB.setName("thread-B");

    threadA.start();
    // sleep(2000); // 동시성 문제 발생X
    sleep(100); // 동시성 문제 발생O -> ThreadLocal 사용으로 발생하지 않음
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
