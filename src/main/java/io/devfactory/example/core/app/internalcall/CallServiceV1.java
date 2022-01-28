package io.devfactory.example.core.app.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// 생성자 주입은 순환 사이클을 만들기 때문에 실패함
// 2.6 이상 부터는 순환 참조 자체를 기본적 허용하지 않음
// 그래서 setter 통한 자기 자신 주입 안되자만 설정을 통해 허용하도록 가능 함
@Slf4j
@Component
public class CallServiceV1 {

  private CallServiceV1 selfCallServiceV1;
  
  // @Autowired // 해당 테스트시 주석 해제 해야 함
  public void setSelfCallServiceV1(CallServiceV1 selfCallServiceV1) {
    log.info("callServiceV1 setter={}", selfCallServiceV1.getClass());
    this.selfCallServiceV1 = selfCallServiceV1;
  }

  public void external() {
    log.info("call v1 external...");
    selfCallServiceV1.internal(); // 외부 메서드 호출
  }

  public void internal() {
    log.info("call v1 internal...");
  }

}
