package io.devfactory;

import io.devfactory.example.jdbc2.TestDataInit;
import io.devfactory.example.jdbc2.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class BasicSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(BasicSpringBootApplication.class, args);
  }

  @Profile("local")
  @Bean
  public TestDataInit testDataInit(
      @Qualifier("jpaItemRepositoryV3") ItemRepository itemRepository) {
    return new TestDataInit(itemRepository);
  }

  // http 요청/응답 기록
  @Bean
  public InMemoryHttpExchangeRepository httpExchangeRepository() {
    return new InMemoryHttpExchangeRepository();
  }

}
