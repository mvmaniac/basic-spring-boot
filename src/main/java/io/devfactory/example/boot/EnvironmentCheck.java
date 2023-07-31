package io.devfactory.example.boot;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EnvironmentCheck {

  private final Environment environment;

  @Value("${JAVA_HOME}")
  private String valueJavaHome;

  @Value("${memory:''}")
  private String valueMemory;

  @Value("${key1:''}")
  private String valueKey1;

  @Value("${key2:''}")
  private String valueKey2;

  public EnvironmentCheck(Environment environment) {
    this.environment = environment;
  }

  // OS, VM options, Program Arguments 모두 다 가져올 수 있음
  @PostConstruct
  public void init() {
    log.debug("[dev] ============================================================================");

    // OS System
    final var javaHome = environment.getProperty("JAVA_HOME");
    log.debug("[dev] used environment, javaHome = {}", javaHome);
    log.debug("[dev] used @Value, javaHome = {}", valueJavaHome);

    // VM Options
    final var memory = environment.getProperty("memory");
    log.debug("[dev] used environment, memory = {}", memory);
    log.debug("[dev] used @Value, valueMemory = {}", valueMemory);

    // Program Arguments
    final var value1 = environment.getProperty("key1"); // key=value 형식도 알아서 가져옴...
    final var value2 = environment.getProperty("key2");

    log.debug("[dev] used environment, key1 = {}", value1);
    log.debug("[dev] used environment, key2 = {}", value2);
    log.debug("[dev] used @Value, key1 = {}", valueKey1);
    log.debug("[dev] used @Value, key2 = {}", valueKey2);
  }

}
