package io.devfactory.example.core.config.v5_auto_proxy;

import io.devfactory.example.core.config.Proxy1Config;
import io.devfactory.example.core.config.Proxy2Config;
import io.devfactory.example.core.config.v3_proxy_factory.advice.LogTraceAdvice;
import io.devfactory.example.core.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({Proxy1Config.class, Proxy2Config.class})
@Configuration
public class AutoProxyConfig {

  // @Bean
  public Advisor advisor1(LogTrace logTrace) {
    // pointcut + advice = advisor
    // pointcut
    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    pointcut.setMappedNames("request*", "order*", "save*");

    // advice
    LogTraceAdvice advice = new LogTraceAdvice(logTrace);
    return new DefaultPointcutAdvisor(pointcut, advice);
  }

  // @Bean
  public Advisor advisor2(LogTrace logTrace) {
    // pointcut + advice = advisor
    // pointcut
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression("execution(* io.devfactory.example.core.app..*(..))");

    // advice
    LogTraceAdvice advice = new LogTraceAdvice(logTrace);
    return new DefaultPointcutAdvisor(pointcut, advice);
  }

  @Bean
  public Advisor advisor3(LogTrace logTrace) {
    // pointcut + advice = advisor
    // pointcut
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression("execution(* io.devfactory.example.core.app..*(..)) && !execution(* io.devfactory.example.core.app..noLog(..))");

    // advice
    LogTraceAdvice advice = new LogTraceAdvice(logTrace);
    return new DefaultPointcutAdvisor(pointcut, advice);
  }

}
