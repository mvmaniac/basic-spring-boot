package io.devfactory.example.core.pointcut;

import io.devfactory.example.core.app.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"squid:S2699", "squid:S5976"})
@Slf4j
class ExecutionTest {

  private final AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
  private Method helloMethod;

  @BeforeEach
  public void init() throws NoSuchMethodException {
    helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
  }

  @DisplayName("메소드 출력")
  @Test
  void printMethod() {
    log.info("helloMethod={}", helloMethod);
  }

  @DisplayName("정확한 매치")
  @Test
  void exactMatch() {
    // public java.lang.String io.devfactory.example.core.app.member.MemberServiceImpl.hello(java.lang.String)
    pointcut.setExpression("execution(public String io.devfactory.example.core.app.member.MemberServiceImpl.hello(String))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @DisplayName("많이 생략한 매치")
  @Test
  void allMatch() {
    pointcut.setExpression("execution(* *(..))"); // 반환타입 메서드이름(파라미터)
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @DisplayName("이름 매치")
  @Test
  void nameMatch() {
    pointcut.setExpression("execution(* hello(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @DisplayName("이름 패턴 매치 1")
  @Test
  void nameMatchStar1() {
    pointcut.setExpression("execution(* hel*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @DisplayName("이름 패턴 매치 2")
  @Test
  void nameMatchStar2() {
    pointcut.setExpression("execution(* *el*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @DisplayName("이름 패턴 매치 실패")
  @Test
  void nameMatchFalse() {
    pointcut.setExpression("execution(* no(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
  }

  @DisplayName("패키지 정확한 매치 1")
  @Test
  void packageExactMatch1() {
    pointcut.setExpression("execution(* io.devfactory.example.core.app.member.MemberServiceImpl.hello(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @DisplayName("패키지 정확한 매치 2")
  @Test
  void packageExactMatch2() {
    pointcut.setExpression("execution(* io.devfactory.example.core.app.member.*.*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @DisplayName("패키지 정확한 매치 실패")
  @Test
  void packageExactMatchFalse() {
    pointcut.setExpression("execution(* io.devfactory.example.core.app.*.*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
  }

  @DisplayName("서브 패키지 패턴 매치 1")
  @Test
  void packageMatchSubPackage1() {
    pointcut.setExpression("execution(* io.devfactory.example.core.app.member..*.*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @DisplayName("서브 패키지 패턴 매치 2")
  @Test
  void packageMatchSubPackage2() {
    pointcut.setExpression("execution(* io.devfactory.example.core.app..*.*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @DisplayName("타입 정확한 매치")
  @Test
  void typeExactMatch() {
    pointcut.setExpression("execution(* io.devfactory.example.core.app.member.MemberServiceImpl.*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @DisplayName("타입 매치 - 인터페이스")
  @Test
  void typeMatchSuperType() {
    pointcut.setExpression("execution(* io.devfactory.example.core.app.member.MemberService.*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @DisplayName("타입 매치 - 구현체")
  @Test
  void typeMatchInternal() throws NoSuchMethodException {
    pointcut.setExpression("execution(* io.devfactory.example.core.app.member.MemberServiceImpl.*(..))");

    Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
    assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
  }

  @DisplayName("타입 매치 실패 - 인터페이스")
  @Test
  void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
    pointcut.setExpression("execution(* io.devfactory.example.core.app.member.MemberService.*(..))");

    Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
    assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
  }

  @DisplayName("파라미터 매치 - String 타입 파라미터 허용")
  @Test
  void argsMatch() {
    pointcut.setExpression("execution(* *(String))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @DisplayName("파라미터 매치 실패 - 파라미터가 없어야 함")
  @Test
  void argsMatchNoArgs() {
    pointcut.setExpression("execution(* *())");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
  }

  @DisplayName("파라미터 패턴 매치 - 정확히 하나의 파라미터 허용, 모든 타입 허용")
  @Test
  void argsMatchStar() {
    pointcut.setExpression("execution(* *(*))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @DisplayName("파라미터 패턴 매치 - 숫자와 무관하게 모든 파라미터, 모든 타입 허용")
  @Test
  void argsMatchAll() {
    pointcut.setExpression("execution(* *(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @DisplayName("파라미터 패턴 매치 - String 타입으로 시작, 숫자와 무관하게 모든 파라미터, 모든 타입 허용")
  @Test
  void argsMatchComplex() {
    pointcut.setExpression("execution(* *(String, ..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

}
