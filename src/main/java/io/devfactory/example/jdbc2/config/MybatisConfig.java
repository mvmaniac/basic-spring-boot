package io.devfactory.example.jdbc2.config;

import io.devfactory.example.jdbc2.repository.ItemRepository;
import io.devfactory.example.jdbc2.repository.mybatis.ItemMapper;
import io.devfactory.example.jdbc2.repository.mybatis.MybatisItemRepository;
import io.devfactory.example.jdbc2.service.ItemService;
import io.devfactory.example.jdbc2.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class MybatisConfig {

  private final ItemMapper itemMapper;

  @Bean("mybatisItemService")
  public ItemService itemService() {
    return new ItemServiceV1(itemRepository());
  }

  @Bean("mybatisItemRepository")
  public ItemRepository itemRepository() {
    return new MybatisItemRepository(itemMapper);
  }

}
