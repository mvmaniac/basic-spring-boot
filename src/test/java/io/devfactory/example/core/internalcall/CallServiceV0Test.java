package io.devfactory.example.core.internalcall;

import io.devfactory.example.core.app.internalcall.aop.CallLogAspect;
import io.devfactory.example.core.app.internalcall.CallServiceV0;
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
class CallServiceV0Test {

  @Autowired
  private CallServiceV0 callServiceV0;

  @DisplayName("external() 메소드안에서 internal() 메소드가 호출되지만 aop 적용은 되지 않음")
  @Test
  void external() {
    callServiceV0.external();
  }

  @DisplayName("바로 internal() 메소드를 호출하므로 aop 적용은 됨")
  @Test
  void internal() {
    callServiceV0.internal();
  }

}
