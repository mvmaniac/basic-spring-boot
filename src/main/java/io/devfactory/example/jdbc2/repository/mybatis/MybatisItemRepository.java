package io.devfactory.example.jdbc2.repository.mybatis;

import io.devfactory.example.jdbc2.domain.Item;
import io.devfactory.example.jdbc2.repository.ItemRepository;
import io.devfactory.example.jdbc2.repository.ItemSearchCond;
import io.devfactory.example.jdbc2.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MybatisItemRepository implements ItemRepository {

  private final ItemMapper itemMapper;

  @Override
  public Item save(Item item) {
    itemMapper.save(item);
    return item;
  }

  @Override
  public void update(Long itemId, ItemUpdateDto updateParam) {
    itemMapper.update(itemId, updateParam);
  }

  @Override
  public Optional<Item> findById(Long id) {
    return itemMapper.findById(id);
  }

  @Override
  public List<Item> findAll(ItemSearchCond cond) {
    return itemMapper.findAll(cond);
  }

}
