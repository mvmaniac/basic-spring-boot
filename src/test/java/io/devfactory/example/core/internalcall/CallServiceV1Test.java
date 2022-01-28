package io.devfactory.example.core.internalcall;

import io.devfactory.example.core.app.internalcall.aop.CallLogAspect;
import io.devfactory.example.core.app.internalcall.CallServiceV1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SuppressWarnings("squid:S2699")
@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest(properties = "spring.main.allow-circular-references=true") // 2.6 이상 부터는 순환참조를 기본적으로 막았기 떄문에 허용 시켜 주어야 함
class CallServiceV1Test {

  @Autowired
  CallServiceV1 callServiceV1;

  @DisplayName("자기 자신 주입을 통한 프록시 객체 사용")
  @Test
  void external() {
    callServiceV1.external();
  }

  @DisplayName("원래 잘됨")
  @Test
  void internal() {
    callServiceV1.internal();
  }

}
