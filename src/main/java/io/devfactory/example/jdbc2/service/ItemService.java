package io.devfactory.example.jdbc2.service;

import io.devfactory.example.jdbc2.domain.Item;
import io.devfactory.example.jdbc2.repository.ItemSearchCond;
import io.devfactory.example.jdbc2.repository.ItemUpdateDto;

import java.util.List;
import java.util.Optional;

public interface ItemService {

  Item save(Item item);

  void update(Long itemId, ItemUpdateDto updateParam);

  Optional<Item> findById(Long id);

  List<Item> findItems(ItemSearchCond itemSearch);

}
