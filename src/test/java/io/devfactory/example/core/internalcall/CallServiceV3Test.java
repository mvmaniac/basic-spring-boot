package io.devfactory.example.core.internalcall;

import io.devfactory.example.core.app.internalcall.CallServiceV3;
import io.devfactory.example.core.app.internalcall.InternalService;
import io.devfactory.example.core.app.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SuppressWarnings("squid:S1186")
@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV3Test {

  @Autowired
  private CallServiceV3 callServiceV3;

  @Autowired
  private InternalService internalService;

  @DisplayName("internal() 메서드를 분리해서 별도의 서비스를 만들어서 구조를 변경")
  @Test
  void external() {
    callServiceV3.external();
  }

  @DisplayName("InternalService 호출로 변경")
  @Test
  void internal() {
    internalService.internal();
  }

}
