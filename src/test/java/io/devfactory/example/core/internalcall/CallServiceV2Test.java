package io.devfactory.example.core.internalcall;

import io.devfactory.example.core.app.internalcall.CallServiceV2;
import io.devfactory.example.core.app.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SuppressWarnings("squid:S2699")
@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV2Test {

  @Autowired
  private CallServiceV2 callServiceV2;

  @DisplayName("ObjectProvider를 사용해서 지연로딩을 통한 프록시 객체 사용")
  @Test
  void external() {
    callServiceV2.external();
  }

  @DisplayName("원래 잘됨")
  @Test
  void internal() {
    callServiceV2.internal();
  }

}
