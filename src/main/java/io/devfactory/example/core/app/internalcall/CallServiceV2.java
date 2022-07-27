package io.devfactory.example.core.app.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

// ObjectProvider(Provider), ApplicationContext를 사용해서 지연(Lazy) 조회
@Slf4j
@Component
public class CallServiceV2 {

  private final ApplicationContext applicationContext;
  private final ObjectProvider<CallServiceV2> callServiceProvider;

  public CallServiceV2(ApplicationContext applicationContext,
      ObjectProvider<CallServiceV2> callServiceProvider) {
    this.applicationContext = applicationContext;
    this.callServiceProvider = callServiceProvider;
  }

  public void external() {
    log.info("call v2 external...");

    // applicationContext에는 많은 기능이 있기 떄문에 ObjectProvider 사용을 추천함
    // CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);

    CallServiceV2 callServiceV2 = callServiceProvider.getObject();
    callServiceV2.internal(); // 외부 메서드 호출
  }

  public void internal() {
    log.info("call v2 internal...");
  }

}
