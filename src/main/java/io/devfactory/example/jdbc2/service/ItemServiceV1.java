package io.devfactory.example.jdbc2.service;

import io.devfactory.example.jdbc2.domain.Item;
import io.devfactory.example.jdbc2.repository.ItemRepository;
import io.devfactory.example.jdbc2.repository.ItemSearchCond;
import io.devfactory.example.jdbc2.repository.ItemUpdateDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceV1 implements ItemService {

  private final ItemRepository itemRepository;

  public ItemServiceV1(@Qualifier("jdbcTemplateV3ItemRepository") ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  @Override
  public Item save(Item item) {
    return itemRepository.save(item);
  }

  @Override
  public void update(Long itemId, ItemUpdateDto updateParam) {
    itemRepository.update(itemId, updateParam);
  }

  @Override
  public Optional<Item> findById(Long id) {
    return itemRepository.findById(id);
  }

  @Override
  public List<Item> findItems(ItemSearchCond cond) {
    return itemRepository.findAll(cond);
  }

}
