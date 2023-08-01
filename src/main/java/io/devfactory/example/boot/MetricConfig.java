package io.devfactory.example.boot;

import io.devfactory.example.boot.service.MetricServiceV4;
import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class MetricConfig {

  private final MetricServiceV4 metricService;

  @Bean
  public CountedAspect countedAspect(MeterRegistry registry) {
    return new CountedAspect(registry);
  }

  @Bean
  public TimedAspect timedAspect(MeterRegistry registry) {
    return new TimedAspect(registry);
  }

  // 별도의 Bean으로 등록해서 사용
//  @Bean
//  public InitializingBean initMetricGaugeV1() {
//    return () -> Gauge.builder("metric.stock", metricService,
//        service -> {
//          log.info("stock gauge call");
//          return service.getStock().get();
//        }).register(meterRegistry);
//  }

  // MeterBinder로 단순하게 등록해서 사용
  @Bean
  public MeterBinder initMetricGaugeV2() {
    return registry -> Gauge.builder("metric.stock", metricService, service -> {
      log.info("stock gauge call");
      return service.getStock().get();
    }).register(registry);
  }

}
