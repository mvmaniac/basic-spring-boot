package io.devfactory.example.boot.selector;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

class HelloImportSelectorTest {

  @DisplayName("정적인 방법")
  @Test
  void staticConfig() {
    final var applicationContext = new AnnotationConfigApplicationContext(StaticConfig.class);
    final var bean = applicationContext.getBean(HelloBean.class);
    assertThat(bean).isNotNull();
  }

  @Import(HelloConfig.class)
  @Configuration
  public static class StaticConfig {

  }

  @DisplayName("동적인 방법")
  @Test
  void selectorConfig() {
    final var applicationContext = new AnnotationConfigApplicationContext(SelectorConfig.class);
    final var bean = applicationContext.getBean(HelloBean.class);
    assertThat(bean).isNotNull();
  }

  @Import(HelloImportSelector.class)
  @Configuration
  public static class SelectorConfig {

  }

}
