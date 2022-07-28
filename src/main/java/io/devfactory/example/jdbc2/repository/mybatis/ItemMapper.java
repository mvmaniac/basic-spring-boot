package io.devfactory.example.jdbc2.repository.mybatis;

import io.devfactory.example.jdbc2.domain.Item;
import io.devfactory.example.jdbc2.repository.ItemSearchCond;
import io.devfactory.example.jdbc2.repository.ItemUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ItemMapper {

  void save(Item item);

  void update(@Param("id") Long id, @Param("updateParam") ItemUpdateDto updateParam);

  Optional<Item> findById(Long id);

  List<Item> findAll(ItemSearchCond itemSearch);

}
