package io.devfactory.example.core.config.v4_postprocessor;

import io.devfactory.example.core.config.v3_proxy_factory.advice.LogTraceAdvice;
import io.devfactory.example.core.config.v4_postprocessor.postprocesor.PackageLogTracePostProcessor;
import io.devfactory.example.core.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

// AutoProxyConfig에서 사용하므로 여기는 주석 처리함
@Slf4j
// @Import({Proxy1Config.class, Proxy2Config.class})
// @Configuration
public class BeanPostProcessorConfig {

  // @Bean
  public PackageLogTracePostProcessor logTracePostProcessor(LogTrace logTrace) {
    return new PackageLogTracePostProcessor("io.devfactory.example.core.app.proxy", getAdvisor(logTrace));
  }

  private Advisor getAdvisor(LogTrace logTrace) {
    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    pointcut.setMappedNames("request*", "order*", "save*");

    LogTraceAdvice advice = new LogTraceAdvice(logTrace);
    return new DefaultPointcutAdvisor(pointcut, advice);
  }

}
