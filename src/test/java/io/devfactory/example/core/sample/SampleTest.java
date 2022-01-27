package io.devfactory.example.core.sample;

import io.devfactory.example.core.app.sample.SampleService;
import io.devfactory.example.core.app.sample.aop.RetryAspect;
import io.devfactory.example.core.app.sample.aop.TraceAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SuppressWarnings("squid:S2699")
@Slf4j
@Import({TraceAspect.class, RetryAspect.class})
@SpringBootTest
class SampleTest {

  @Autowired
  private SampleService sampleService;

  @DisplayName("TraceAspect 테스트")
  @Test
  void traceAspectTest() {
    for (int i = 0; i < 5; i += 1) {
      log.info("client request i={}", i);
      sampleService.request("data" + i);
    }
  }

}
