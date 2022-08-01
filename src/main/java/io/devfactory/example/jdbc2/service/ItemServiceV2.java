package io.devfactory.example.jdbc2.service;

import io.devfactory.example.jdbc2.domain.Item;
import io.devfactory.example.jdbc2.repository.ItemSearchCond;
import io.devfactory.example.jdbc2.repository.ItemUpdateDto;
import io.devfactory.example.jdbc2.repository.v2.ItemQueryRepositoryV2;
import io.devfactory.example.jdbc2.repository.v2.ItemRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemServiceV2 implements ItemService {

  private final ItemRepositoryV2 itemRepositoryV2;

  private final ItemQueryRepositoryV2 itemQueryRepositoryV2;

  @Override
  public Item save(Item item) {
    return itemRepositoryV2.save(item);
  }

  @Transactional
  @Override
  public void update(Long itemId, ItemUpdateDto updateParam) {
    final var foundItem = itemRepositoryV2.findById(itemId).orElseThrow();
    foundItem.setItemName(updateParam.getItemName());
    foundItem.setPrice(updateParam.getPrice());
    foundItem.setQuantity(updateParam.getQuantity());
  }

  @Override
  public Optional<Item> findById(Long id) {
    return itemRepositoryV2.findById(id);
  }

  @Override
  public List<Item> findItems(ItemSearchCond cond) {
    return itemQueryRepositoryV2.findAll(cond);
  }

}
