package io.devfactory.example.core.config.v6_aop;

import io.devfactory.example.core.config.Proxy1Config;
import io.devfactory.example.core.config.Proxy2Config;
import io.devfactory.example.core.config.v6_aop.aspect.LogTraceAspect;
import io.devfactory.example.core.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({Proxy1Config.class, Proxy2Config.class})
@Configuration
public class AopConfig {

  @Bean
  public LogTraceAspect logTraceAspect(LogTrace logTrace) {
    return new LogTraceAspect(logTrace);
  }

}
