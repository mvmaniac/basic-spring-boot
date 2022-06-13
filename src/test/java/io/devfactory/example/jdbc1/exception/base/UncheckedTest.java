package io.devfactory.example.jdbc1.exception.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class UncheckedTest {

  @Test
  void unchecked_catch() {
    Service service = new Service();
    service.callCatch();
  }

  @Test
  void unchecked_throw() {
    Service service = new Service();
    assertThatThrownBy(service::callThrow).isInstanceOf(MyUncheckedException.class);
  }

  // RuntimeException을 상속받은 예외는 언체크 예외가 됨
  static class MyUncheckedException extends RuntimeException {

    public MyUncheckedException(String message) {
      super(message);
    }

  }

  // Unhecked 예외는 예외를 잡거나, 던지지 않아도 됨
  static class Service {

    Repository repository = new Repository();

    // 필요한 경우 예외를 잡아서 처리하면 됨
    public void callCatch() {
      try {
        repository.call();
      } catch (MyUncheckedException e) {
        // 예외 처리 로직
        log.info("예외 처리, message={}", e.getMessage(), e);
      }
    }

    // 예외를 잡지 않아도 됨, 자연스럽게 상위로 넘어감
    public void callThrow() {
      repository.call();
    }

  }

  static class Repository {

    public void call() {
      throw new MyUncheckedException("ex");
    }

  }

}
