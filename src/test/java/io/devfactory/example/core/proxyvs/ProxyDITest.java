package io.devfactory.example.core.proxyvs;

import io.devfactory.example.core.app.member.MemberService;
import io.devfactory.example.core.app.member.MemberServiceImpl;
import io.devfactory.example.core.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SuppressWarnings("squid:S1186")
@Slf4j
@Import(ProxyDIAspect.class)
// @SpringBootTest(properties = "spring.aop.proxy-target-class=false")
@SpringBootTest(properties = "spring.aop.proxy-target-class=true")
class ProxyDITest {

  @Autowired
  private MemberService memberService;

  // JDK 동적 프록시는 대상 객체인 MemberServiceImpl 타입에 의존관게를 주입할 수 없음, 인터페이스로는 가능
  // CGLIB 프록시는 대상 객체인 MemberServiceImpl 타입에 의존관게를 주입할 수 있음
  @Autowired
  private MemberServiceImpl memberServiceImpl;

  @DisplayName("프록시 의존관계 주입 테스트")
  @Test
  void go() {
    log.info("memberService class={}", memberService.getClass());
    log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
    memberServiceImpl.hello("hello");
  }

}
