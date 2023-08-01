package io.devfactory.example.boot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogController {

  // actuator 통해 실시간으로 로그 레벨을 변경할 수 있음, actuator.http 파일 참고
  @GetMapping("/log")
  public String log() {
    log.trace("trace log...");
    log.debug("debug log...");
    log.info("info log...");
    log.warn("warn log...");
    log.error("error log...");
    return "ok";
  }

}
