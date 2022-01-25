package io.devfactory.example.core.pointcut;

import io.devfactory.example.core.app.member.MemberService;
import io.devfactory.example.core.app.member.annotation.ClassAop;
import io.devfactory.example.core.app.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SuppressWarnings({"squid:S2699"})
@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
class ParameterTest {

  @Autowired
  private MemberService memberService;
  
  @DisplayName("파라미터 테스트")
  @Test
  void success() {
    log.info("memberService Proxy={}", memberService.getClass());
    memberService.hello("helloA");
  }

  @Aspect
  static class ParameterAspect {

    @Pointcut("execution(* io.devfactory.example.core.app.member..*.*(..))")
    private void allMember() {
      // nothing
    }

    @Around("allMember()")
    public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
      Object arg = joinPoint.getArgs()[0];
      log.info("[logArgs1]{}, arg={}", joinPoint.getSignature(), arg);
      return joinPoint.proceed();
    }

    // Object가 아닌 String으로 받을 수 있음
    @Around("allMember() && args(arg, ..)")
    public Object logArgs1(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
      log.info("[logArgs2]{}, arg={}", joinPoint.getSignature(), arg);
      return joinPoint.proceed();
    }

    @Before("allMember() && args(arg, ..)")
    public void logArgs3(String arg) {
      log.info("[logArgs3] arg={}", arg);
    }

    @Before(value = "allMember() && this(obj)", argNames = "joinPoint,obj")
    public void thisArgs(JoinPoint joinPoint, MemberService obj) {
      log.info("[this]{}, obj={}", joinPoint.getSignature(), obj.getClass());
    }

    @Before("allMember() && target(obj)")
    public void targetArgs(JoinPoint joinPoint, MemberService obj) {
      log.info("[target]{}, obj={}", joinPoint.getSignature(), obj.getClass());
    }

    @Before("allMember() && @target(annotation)")
    public void atTargetArgs(JoinPoint joinPoint, ClassAop annotation) {
      log.info("[@target]{}, annotation={}", joinPoint.getSignature(), annotation);
    }

    @Before("allMember() && @within(annotation)")
    public void atWithinArgs(JoinPoint joinPoint, ClassAop annotation) {
      log.info("[@within]{}, annotation={}", joinPoint.getSignature(), annotation);
    }

    @Before("allMember() && @annotation(annotation)")
    public void atAnnotationArgs(JoinPoint joinPoint, MethodAop annotation) {
      log.info("[@annotation]{}, annotationValue={}", joinPoint.getSignature(), annotation.value());
    }

  }

}
