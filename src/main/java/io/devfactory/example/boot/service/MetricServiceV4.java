package io.devfactory.example.boot.service;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// v4, Annotation 사용(MetricConfig 설정 확인), Timer
@Timed("metric.order")
@Slf4j
@Service
public class MetricServiceV4 implements MetricService {

  private final AtomicInteger stock = new AtomicInteger(100);

  @Counted("metric.order")
  @Override
  public void order() {
    log.info("주문");
    stock.decrementAndGet();
    sleep(500);
  }

  @Counted("metric.order")
  @Override
  public void cancel() {
    log.info("취소");
    stock.incrementAndGet();
    sleep(200);
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
