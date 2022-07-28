package io.devfactory.example.jdbc2.config;

import io.devfactory.example.jdbc2.repository.ItemRepository;
import io.devfactory.example.jdbc2.repository.jpa.JpaItemRepositoryV2;
import io.devfactory.example.jdbc2.repository.jpa.SpringDataJpaItemRepository;
import io.devfactory.example.jdbc2.service.ItemService;
import io.devfactory.example.jdbc2.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SpringDataJpaConfig {

  private final SpringDataJpaItemRepository repository;

  @Bean("jpaItemServiceV2")
  public ItemService itemService() {
    return new ItemServiceV1(itemRepository());
  }

  @Bean("jpaItemRepositoryV2")
  public ItemRepository itemRepository() {
    return new JpaItemRepositoryV2(repository);
  }

}
