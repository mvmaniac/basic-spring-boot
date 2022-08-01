package io.devfactory.example.jdbc2.repository.jpa;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.devfactory.example.jdbc2.domain.Item;
import io.devfactory.example.jdbc2.repository.ItemRepository;
import io.devfactory.example.jdbc2.repository.ItemSearchCond;
import io.devfactory.example.jdbc2.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static io.devfactory.example.jdbc2.domain.QItem.item;

@Slf4j
@Transactional(readOnly = true)
@Repository
public class JpaItemRepositoryV3 implements ItemRepository {

  private final EntityManager em;
  private final JPAQueryFactory query;

  public JpaItemRepositoryV3(EntityManager em) {
    this.em = em;
    this.query = new JPAQueryFactory(em);
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
    String itemName = cond.getItemName();
    Integer maxPrice = cond.getMaxPrice();

    // @formatter:off
    return query
      .select(item)
      .from(item)
      .where(likeItemName(itemName), maxPrice(maxPrice))
      .fetch();
    // @formatter:on
  }

  private BooleanExpression likeItemName(String itemName) {
    if (StringUtils.hasText(itemName)) {
      return item.itemName.like("%" + itemName + "%");
    }
    return null;
  }

  private BooleanExpression maxPrice(Integer maxPrice) {
    if (maxPrice != null) {
      return item.price.loe(maxPrice);
    }
    return null;
  }

}
