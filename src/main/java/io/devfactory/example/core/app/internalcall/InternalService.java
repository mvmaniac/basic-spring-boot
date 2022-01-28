package io.devfactory.example.core.app.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InternalService {

  public void internal() {
    log.info("call v3 internal...");
  }

}
