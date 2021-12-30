package io.devfactory.example.core.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class FieldService {

  private String nameStore;

  public String logic(String name) {
    log.info("저장 name={} -> nameStore={}", name, nameStore);
    nameStore = name;

    sleep(1000);

    log.info("조회 nameStore={}", nameStore);
    return nameStore;
  }

  private void sleep(long millis) {
    try {
      TimeUnit.MILLISECONDS.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

}
