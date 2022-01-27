package io.devfactory.example.core.app.sample;

import io.devfactory.example.core.app.sample.annotation.Retry;
import io.devfactory.example.core.app.sample.annotation.Trace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"squid:S1172", "squid:S2696", "squid:S3008"})
@Slf4j
@Repository
public class SampleRepository {

  private static int SEQ = 0;

  // 5번에 1번 실패하는 요청
  @Retry(4)
  @Trace
  public String save(String itemId) {
    SEQ += 1;

    if (SEQ % 5 == 0) {
      log.info("예외발생...");
      throw new IllegalStateException();
    }

    return "ok";
  }

}
