package io.devfactory.example.boot.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// v3, MeterRegistry 직접 사용, Timer
@Slf4j
@RequiredArgsConstructor
@Service
public class MetricServiceV3 implements MetricService {

  private final MeterRegistry meterRegistry;

  private final AtomicInteger stock = new AtomicInteger(100);

  @Override
  public void order() {
    Timer timer = Timer.builder("metric.order")
        .tag("class", this.getClass().getName())
        .tag("method", "order")
        .description("order")
        .register(meterRegistry);

    timer.record(() -> {
      log.info("주문");
      stock.decrementAndGet();
      sleep(500);
    });
  }

  @Override
  public void cancel() {
    Timer timer = Timer.builder("metric.order")
        .tag("class", this.getClass().getName())
        .tag("method", "cancel")
        .description("order")
        .register(meterRegistry);

    timer.record(() -> {
      log.info("취소");
      stock.incrementAndGet();
      sleep(200);
    });
  }

  @Override
  public AtomicInteger getStock() {
    return stock;
  }

  private static void sleep(long l) {
    try {
      TimeUnit.MILLISECONDS.sleep(l + ThreadLocalRandom.current().nextInt(200));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

}
