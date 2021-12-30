package io.devfactory.example.core;

import io.devfactory.example.core.trace.logtrace.LogTrace;
import io.devfactory.example.core.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

  @Bean
  public LogTrace logTrace() {
    return new ThreadLocalLogTrace();
  }

}
