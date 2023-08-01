package io.devfactory.example.boot.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

// v1, MeterRegistry 직접 사용, Count
@Slf4j
@RequiredArgsConstructor
@Service
public class MetricServiceV1 implements MetricService {

  private final MeterRegistry meterRegistry;

  private final AtomicInteger stock = new AtomicInteger(100);

  @Override
  public void order() {
    log.info("주문");
    stock.decrementAndGet();

    Counter.builder("metric.order")
        .tag("class", this.getClass().getName())
        .tag("method", "order")
        .description("order")
        .register(meterRegistry).increment();
  }

  @Override
  public void cancel() {
    log.info("취소");
    stock.incrementAndGet();

    Counter.builder("metric.order")
        .tag("class", this.getClass().getName())
        .tag("method", "cancel")
        .description("order")
        .register(meterRegistry).increment();
  }

  @Override
  public AtomicInteger getStock() {
    return stock;
  }

}
