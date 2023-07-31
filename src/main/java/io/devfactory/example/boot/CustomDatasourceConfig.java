package io.devfactory.example.boot;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@EnableConfigurationProperties(CustomDatasourceProperties.class) //or @ConfigurationPropertiesScan
@Configuration
public class CustomDatasourceConfig {

  private final CustomDatasourceProperties customDatasourceProperties;

  public CustomDatasourceConfig(CustomDatasourceProperties customDatasourceProperties) {
    this.customDatasourceProperties = customDatasourceProperties;
  }

  @PostConstruct
  public void postConstruct() {
    log.debug("[dev] ============================================================================");
    log.debug("[dev] custom.datasource.url = {}", customDatasourceProperties.getUrl());
    log.debug("[dev] custom.datasource.username = {}", customDatasourceProperties.getUsername());
    log.debug("[dev] custom.datasource.password = {}", customDatasourceProperties.getPassword());

    final var etc = customDatasourceProperties.getEtc();
    log.debug("[dev] custom.datasource.etc.max-connection = {}", etc.getMaxConnection());
    log.debug("[dev] custom.datasource.etc.timeout = {}", etc.getTimeout());
    log.debug("[dev] custom.datasource.etc.options = {}", etc.getOptions());
  }

}
