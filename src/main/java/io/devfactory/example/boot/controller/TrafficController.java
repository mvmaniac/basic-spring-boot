package io.devfactory.example.boot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// grafana 테스트용 컨트롤러
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/traffic")
@RestController
public class TrafficController {

  private final DataSource dataSource;

  @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
  private final List<String> list = new ArrayList<>();

  @GetMapping("cpu")
  public String cpu() {
    log.info("cpu");

    long value = 0;
    for (long i = 0; i < 1_000_000_000_000L; i++) {
      value++;
    }

    return "ok value=" + value;
  }

  @GetMapping("/jvm")
  public String jvm() {
    log.info("jvm");

    for (int i = 0; i < 1_000_000; i++) {
      list.add("hello jvm!" + i);
    }

    return "ok";
  }

  @GetMapping("/jdbc")
  public String jdbc() throws SQLException {
    log.info("jdbc");

    Connection conn = dataSource.getConnection();
    log.info("connection info={}", conn);
    // conn.close(); // 커넥션을 닫지 않는다.

    return "ok";
  }

  @GetMapping("/error-log")
  public String errorLog() {
    log.error("error log");
    return "error";
  }

}
