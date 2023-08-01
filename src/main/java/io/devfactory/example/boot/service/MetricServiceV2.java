package io.devfactory.example.boot.service;

import io.micrometer.core.annotation.Counted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

// v1, Annotation 사용(MetricConfig 설정 확인), Count
@Slf4j
@Service
public class MetricServiceV2 implements MetricService {

  private final AtomicInteger stock = new AtomicInteger(100);

  @Counted("metric.order")
  @Override
  public void order() {
    log.info("주문");
    stock.decrementAndGet();
  }

  @Counted("metric.order")
  @Override
  public void cancel() {
    log.info("취소");
    stock.incrementAndGet();
  }

  @Override
  public AtomicInteger getStock() {
    return stock;
  }

}
