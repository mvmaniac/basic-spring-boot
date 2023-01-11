package io.devfactory.example.jdbc2.repository.v2;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.devfactory.example.jdbc2.domain.Item;
import io.devfactory.example.jdbc2.repository.ItemSearchCond;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import java.util.List;

import static io.devfactory.example.jdbc2.domain.QItem.item;

@Repository
public class ItemQueryRepositoryV2 {

  private final JPAQueryFactory query;

  public ItemQueryRepositoryV2(EntityManager em) {
    this.query = new JPAQueryFactory(em);
  }

  public List<Item> findAll(ItemSearchCond cond) {
    // @formatter:off
    return query
        .select(item)
        .from(item)
        .where(
          likeItemName(cond.getItemName()),
          maxPrice(cond.getMaxPrice())
        ).fetch();
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
