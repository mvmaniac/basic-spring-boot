package io.devfactory.example.core.proxyvs;

import io.devfactory.example.core.app.member.MemberService;
import io.devfactory.example.core.app.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("squid:S2699")
@Slf4j
class ProxyCastingTest {

  @DisplayName("JDK 동적 프록시 캐스팅 테스트")
  @Test
  void jdkProxy() {
    MemberServiceImpl target = new MemberServiceImpl();

    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(false); // JDK 동적 포록시

    // 프록시를 인터페이스로 캐스팅 성공
    MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

    // JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패, ClassCastException 예외 발생
    assertThatThrownBy(() -> {
      MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }).isInstanceOf(ClassCastException.class);
  }

  @DisplayName("CGLIB 프록시 캐스팅 테스트")
  @Test
  void cglibProxy() {
    MemberServiceImpl target = new MemberServiceImpl();

    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(true); // CGLIB 포록시

    // 프록시를 인터페이스로 캐스팅 성공
    MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

    // CGLIB 프록시를 구현 클래스로 캐스팅 시도 성공
    MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
  }

}
