package io.devfactory.example.boot;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;

@Slf4j
public class CommandLineBean {

  private final ApplicationArguments arguments;

  public CommandLineBean(ApplicationArguments arguments) {
    this.arguments = arguments;
  }

  @PostConstruct
  public void init() {
    final var optionNames = arguments.getOptionNames();
    for (String optionName : optionNames) {
      log.debug("[dev] option args key = {}, value = {}", optionName, arguments.getOptionValues(optionName).get(0));
    }
  }

}
