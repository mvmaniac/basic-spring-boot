package io.devfactory.example.core.app.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

// 구조를 분리(변경)
@SuppressWarnings("ClassCanBeRecord")
@Slf4j
@Component
public class CallServiceV3 {

  private final InternalService internalService;

  public CallServiceV3(InternalService internalService) {
    this.internalService = internalService;
  }

  public void external() {
    log.info("call v3 external...");
    internalService.internal(); // 외부 메서드 호출
  }

}
