package io.devfactory.example.jdbc2.repository.jpa;

import io.devfactory.example.jdbc2.domain.Item;
import io.devfactory.example.jdbc2.repository.ItemRepository;
import io.devfactory.example.jdbc2.repository.ItemSearchCond;
import io.devfactory.example.jdbc2.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional(readOnly = true)
@Repository // 해당 어노테이션을 넣어야 스프링 데이터 접근 예외로 변환
public class JpaItemRepository implements ItemRepository {

  private final EntityManager em;

  public JpaItemRepository(EntityManager em) {
    this.em = em;
  }

  @Transactional
  @Override
  public Item save(Item item) {
    em.persist(item);
    return item;
  }

  @Transactional
  @Override
  public void update(Long itemId, ItemUpdateDto updateParam) {
    Item findItem = em.find(Item.class, itemId);
    findItem.setItemName(updateParam.getItemName());
    findItem.setPrice(updateParam.getPrice());
    findItem.setQuantity(updateParam.getQuantity());
  }

  @Override
  public Optional<Item> findById(Long id) {
    Item item = em.find(Item.class, id);
    return Optional.ofNullable(item);
  }

  @Override
  public List<Item> findAll(ItemSearchCond cond) {
    String jpql = "select i from Item i";

    Integer maxPrice = cond.getMaxPrice();
    String itemName = cond.getItemName();

    if (StringUtils.hasText(itemName) || maxPrice != null) {
      jpql += " where";
    }

    boolean andFlag = false;
    if (StringUtils.hasText(itemName)) {
      jpql += " i.itemName like concat('%', :itemName, '%')";
      andFlag = true;
    }

    if (maxPrice != null) {
      if (andFlag) {
        jpql += " and";
      }
      jpql += " i.price <= :maxPrice";
    }

    log.info("jpql={}", jpql);

    TypedQuery<Item> query = em.createQuery(jpql, Item.class);
    if (StringUtils.hasText(itemName)) {
      query.setParameter("itemName", itemName);
    }
    if (maxPrice != null) {
      query.setParameter("maxPrice", maxPrice);
    }
    return query.getResultList();
  }

}
