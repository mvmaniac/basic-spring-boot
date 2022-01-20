package io.devfactory.example.core.app.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
public class OrderRepository {

  public String save(String itemId) {
    log.info("OrderRepository 실행...");

    // 저장 로직
    if ("ex".equals(itemId)) {
      throw new IllegalStateException("예외 발생");
    }

    return "ok";
  }

}
