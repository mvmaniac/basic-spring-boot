package io.devfactory.example.core.pointcut;

import io.devfactory.example.core.app.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * spring.aop.proxy-target-class=true => CGLIB (기본값)
 * spring.aop.proxy-target-class=false => JDK 동적 프록시
 */
@SuppressWarnings({"squid:S2699"})
@Slf4j
@Import(ThisTargetTest.ThisTargetAspect.class)
@SpringBootTest(properties = "spring.aop.proxy-target-class=false")
class ThisTargetTest {

  @Autowired
  private MemberService memberService;

  @DisplayName("this, target 비교 테스트")
  @Test
  void success() {
    log.info("memberService Proxy={}", memberService.getClass());
    memberService.hello("helloA");
  }

  @Aspect
  static class ThisTargetAspect {

    // 부모 타입 허용
    @Around("this(io.devfactory.example.core.app.member.MemberService)")
    public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
      log.info("[this-interface] {}", joinPoint.getSignature());
      return joinPoint.proceed();
    }

    // 부모 타입 허용
    @Around("target(io.devfactory.example.core.app.member.MemberService)")
    public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
      log.info("[target-interface] {}", joinPoint.getSignature());
      return joinPoint.proceed();
    }

    @Around("this(io.devfactory.example.core.app.member.MemberServiceImpl)")
    public Object doThis(ProceedingJoinPoint joinPoint) throws Throwable {
      log.info("[this-impl] {}", joinPoint.getSignature());
      return joinPoint.proceed();
    }

    @Around("target(io.devfactory.example.core.app.member.MemberServiceImpl)")
    public Object doTarget(ProceedingJoinPoint joinPoint) throws Throwable {
      log.info("[target-impl] {}", joinPoint.getSignature());
      return joinPoint.proceed();
    }

  }

}
