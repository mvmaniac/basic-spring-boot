package io.devfactory.example.core.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject {

  private final Subject target;
  private String cacheValue;

  public CacheProxy(Subject target) {
    this.target = target;
  }

  @Override
  public String operation() {
    log.info("프록시 호출");

    if (null == cacheValue) {
      cacheValue = target.operation();
    }

    return cacheValue;
  }

}
