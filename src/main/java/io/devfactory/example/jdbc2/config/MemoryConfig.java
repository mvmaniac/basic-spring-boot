package io.devfactory.example.jdbc2.config;

import io.devfactory.example.jdbc2.repository.ItemRepository;
import io.devfactory.example.jdbc2.repository.memory.MemoryItemRepository;
import io.devfactory.example.jdbc2.service.ItemService;
import io.devfactory.example.jdbc2.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {

  @Bean
  public ItemService itemService() {
    return new ItemServiceV1(itemRepository());
  }

  @Bean
  public ItemRepository itemRepository() {
    return new MemoryItemRepository();
  }

}
