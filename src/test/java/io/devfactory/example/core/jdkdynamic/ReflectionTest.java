package io.devfactory.example.core.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

@SuppressWarnings("squid:S2699")
@Slf4j
class ReflectionTest {
  
  @DisplayName("리플렉션 테스트 0")
  @Test
  void reflectionV0() {
    Hello target = new Hello();

    // 공통 로직1 시작
    log.info("start");
    String resultA = target.callA();
    log.info("resultA={}", resultA);
    // 공통 로직1 종료

    // 공통 로직2 시작
    log.info("start");
    String resultB = target.callB();
    log.info("resultB={}", resultB);
    // 공통 로직2 종료
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @DisplayName("리플렉션 테스트 1")
  @Test
  void reflectionV1() throws Exception {
    // 클래스 정보
    Class classHello = Class.forName("io.devfactory.example.core.jdkdynamic.ReflectionTest$Hello");

    Hello target = new Hello();

    // callA 메서드 정보
    Method methodCallA = classHello.getMethod("callA");
    Object resultA = methodCallA.invoke(target);
    log.info("resultA={}", resultA);

    // callB 메서드 정보
    Method methodCallB = classHello.getMethod("callB");
    Object resultB = methodCallB.invoke(target);
    log.info("resultB={}", resultB);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @DisplayName("리플렉션 테스트 2")
  @Test
  void reflectionV2() throws Exception {
    // 클래스 정보
    Class classHello = Class.forName("io.devfactory.example.core.jdkdynamic.ReflectionTest$Hello");

    Hello target = new Hello();

    // callA 메서드 정보
    Method methodCallA = classHello.getMethod("callA");
    dynamicCall(methodCallA, target);

    // callB 메서드 정보
    Method methodCallB = classHello.getMethod("callB");
    dynamicCall(methodCallB, target);
  }

  private void dynamicCall(Method method, Object target) throws Exception {
    log.info("start");
    Object result = method.invoke(target);
    log.info("result={}", result);
  }

  static class Hello {
    public String callA() {
      log.info("callA");
      return "A";
    }

    public String callB() {
      log.info("callB");
      return "B";
    }
  }

}
