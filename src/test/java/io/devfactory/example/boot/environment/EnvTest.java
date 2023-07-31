package io.devfactory.example.boot.environment;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.List;

@Slf4j
class EnvTest {

  @DisplayName("OS 환경 변수")
  @Test
  void osEnv() {
    final var systemEnv = System.getenv();
    for (String key : systemEnv.keySet()) {
      log.debug("key = {}, value = {}", key, systemEnv.get(key));
    }
  }

  // VM options, -Dxxx=xxx
  @DisplayName("자바 시스템 속성")
  @Test
  void javaSystemEnv() {
    final var systemEnv = System.getProperties();
    for (Object key : systemEnv.keySet()) {
      log.debug("key = {}, value = {}", key, System.getProperty(String.valueOf(key)));
    }
  }

  // 자바에서 기본으로 제공하는 커맨드 라인 인수 (Program Arguments), java -jar test.jar xxx xxx
  // 스프링에서 제공, 커맨드 라인에 --를 연결해서 시작하면 key=value 형식으로 정하고 이것을 커맨드 라인 옵션 인수라 함
  // ex) --key1=value1 --key2=value2 key3=value3
  // ApplicationArguments를 bean으로 등록해서 사용하면 main 메소드가 아닌 다른 곳으로 사용 할 수 있음 (CommandLineBean 참고)
  public static void main(String[] args) {
    for (String arg : args) {
      log.debug("[dev] arg = {}", arg);
    }

    // 커맨드 라인 옵션 인수 확인
    final var arguments = new DefaultApplicationArguments(args);
    log.debug("[dev] getSourceArgs = {}", List.of(arguments.getSourceArgs()));
    log.debug("[dev] getNonOptionArgs = {}", List.of(arguments.getNonOptionArgs()));
    log.debug("[dev] getOptionNames = {}", arguments.getOptionNames());

    final var optionNames = arguments.getOptionNames();
    for (String optionName : optionNames) {
      log.debug("[dev] option args key = {}, value = {}", optionName, arguments.getOptionValues(optionName).get(0));
    }
  }

}
