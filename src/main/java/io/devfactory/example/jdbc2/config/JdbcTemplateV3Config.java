package io.devfactory.example.jdbc2.config;

import io.devfactory.example.jdbc2.repository.ItemRepository;
import io.devfactory.example.jdbc2.repository.jdbctemplate.JdbcTemplateItemRepositoryV3;
import io.devfactory.example.jdbc2.service.ItemService;
import io.devfactory.example.jdbc2.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
public class JdbcTemplateV3Config {

  private final DataSource dataSource;

  @Bean("jdbcTemplateV3ItemService")
  public ItemService itemService() {
    return new ItemServiceV1(itemRepository());
  }

  @Bean("jdbcTemplateV3ItemRepository")
  public ItemRepository itemRepository() {
    return new JdbcTemplateItemRepositoryV3(dataSource);
  }

}
