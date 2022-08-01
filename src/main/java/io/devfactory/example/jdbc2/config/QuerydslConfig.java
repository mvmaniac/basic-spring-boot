package io.devfactory.example.jdbc2.config;

import io.devfactory.example.jdbc2.repository.jpa.JpaItemRepositoryV3;
import io.devfactory.example.jdbc2.service.ItemService;
import io.devfactory.example.jdbc2.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class QuerydslConfig {

  private final JpaItemRepositoryV3 jpaItemRepository;

  @Bean("jpaItemServiceV3")
  public ItemService itemService() {
    return new ItemServiceV1(jpaItemRepository);
  }

}
