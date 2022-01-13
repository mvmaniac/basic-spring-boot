package io.devfactory.example.core.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class BeanPostProcessorTest {

  @DisplayName("beanPostProcessor 테스트")
  @Test
  void beanPostProcessorConfig() {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class);

    // A는 빈으로 등록 되지 않는다.
    assertThatThrownBy(() -> applicationContext.getBean(A.class))
        .isInstanceOf(NoSuchBeanDefinitionException.class);

    // beanA 이름으로 B 객체가 빈으로 등록된다.
    B b = applicationContext.getBean("beanA", B.class);
    b.helloB();
  }

  static class BeanPostProcessorConfig {

    @Bean(name = "beanA")
    public A a() {
      return new A();
    }

    @Bean
    public AToBPostProcessor aToBPostProcessor() {
      return new AToBPostProcessor();
    }

  }

  static class A {

    public void helloA() {
      log.info("hello A");
    }

  }

  static class B {

    public void helloB() {
      log.info("hello B");
    }

  }

  static class AToBPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean,
        @NonNull String beanName) throws BeansException {

      log.info("beanName={}, bean={}", beanName, bean);

      if (bean instanceof A) {
        return new B();
      }

      return bean;
    }

  }

}

