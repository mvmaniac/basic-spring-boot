package io.devfactory.example.core.pointcut;

import io.devfactory.example.core.app.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("squid:S5976")
@Slf4j
class WithinTest {

  private final AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
  private Method helloMethod;

  @BeforeEach
  public void init() throws NoSuchMethodException {
    helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
  }

  @Test
  void withinExact() {
    pointcut.setExpression("within(io.devfactory.example.core.app.member.MemberServiceImpl)");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void withinStar() {
    pointcut.setExpression("within(io.devfactory.example.core.app.member.*Service*)");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void withinSubPackage() {
    pointcut.setExpression("within(io.devfactory.example.core.app..*)");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  @DisplayName("타켓의 타입에만 직접 적용, 인터페이스를 선정하면 안된다.")
  void withinSuperTypeFalse() {
    pointcut.setExpression("within(io.devfactory.example.core.app.member.MemberService)");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
  }

  @Test
  @DisplayName("execution은 타입 기반, 인터페이스 선정 가능")
  void executionSuperTypeTrue() {
    pointcut.setExpression("execution(* io.devfactory.example.core.app.member.MemberService.*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

}
