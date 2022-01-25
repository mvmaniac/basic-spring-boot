package io.devfactory.example.core.pointcut;

import io.devfactory.example.core.app.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SuppressWarnings({"squid:S2699"})
@Slf4j
@Import(AtAnnotationTest.AtAnnotationAspect.class)
@SpringBootTest
class AtAnnotationTest {

  @Autowired
  private MemberService memberService;

  @Test
  void success() {
    log.info("memberService Proxy={}", memberService.getClass());
    memberService.hello("helloA");
  }

  @Slf4j
  @Aspect
  static class AtAnnotationAspect {

    @Around("@annotation(io.devfactory.example.core.app.member.annotation.MethodAop)")
    public Object doAtAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
      log.info("[@annotation] {}", joinPoint.getSignature());
      return joinPoint.proceed();
    }

  }

}
