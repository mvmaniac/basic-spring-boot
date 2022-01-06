package io.devfactory.example.core.pureproxy.proxy;

import io.devfactory.example.core.pureproxy.proxy.code.CacheProxy;
import io.devfactory.example.core.pureproxy.proxy.code.ProxyPatternClient;
import io.devfactory.example.core.pureproxy.proxy.code.RealSubject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("squid:S2699")
@Slf4j
class ProxyPatternTest {
  
  @DisplayName("프록시 패턴 적용 전 테스트")
  @Test
  void noProxy() {
    RealSubject realSubject = new RealSubject();
    ProxyPatternClient client = new ProxyPatternClient(realSubject);
    
    client.execute();
    client.execute();
    client.execute();
  }

  @DisplayName("프록시 패턴 적용 후 테스트")
  @Test
  void cacheProxy() {
    RealSubject realSubject = new RealSubject();
    CacheProxy cacheProxy = new CacheProxy(realSubject);
    ProxyPatternClient client = new ProxyPatternClient(cacheProxy);

    client.execute();
    client.execute();
    client.execute();
  }

}
