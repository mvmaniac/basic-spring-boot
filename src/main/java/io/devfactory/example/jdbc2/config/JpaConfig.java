package io.devfactory.example.jdbc2.config;

import io.devfactory.example.jdbc2.repository.jpa.JpaItemRepository;
import io.devfactory.example.jdbc2.service.ItemService;
import io.devfactory.example.jdbc2.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JpaConfig {

  private final JpaItemRepository jpaItemRepository;

  @Bean("jpaItemService")
  public ItemService itemService() {
    return new ItemServiceV1(jpaItemRepository);
  }

}
