package io.devfactory.example.jdbc2.repository.jdbctemplate;

import io.devfactory.example.jdbc2.domain.Item;
import io.devfactory.example.jdbc2.repository.ItemRepository;
import io.devfactory.example.jdbc2.repository.ItemSearchCond;
import io.devfactory.example.jdbc2.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.StringUtils.hasText;

// NamedParameterJdbcTemplate
@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection", "DuplicatedCode"})
@Slf4j
public class JdbcTemplateItemRepositoryV2 implements ItemRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public JdbcTemplateItemRepositoryV2(DataSource dataSource) {
    this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

  @Override
  public Item save(Item item) {
    String sql = "insert into item(item_name, price, quantity) " +
        "values (:itemName, :price, :quantity)";

    SqlParameterSource param = new BeanPropertySqlParameterSource(item);

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(sql, param, keyHolder);

    long key = requireNonNull(keyHolder.getKey()).longValue();
    item.setId(key);
    return item;
  }

  @Override
  public void update(Long itemId, ItemUpdateDto updateParam) {
    String sql = "update item " +
        "set item_name = :itemName, price = :price, quantity = :quantity " +
        "where id = :id";

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("itemName", updateParam.getItemName())
        .addValue("price", updateParam.getPrice())
        .addValue("quantity", updateParam.getQuantity())
        .addValue("id", itemId); //이 부분이 별도로 필요하다.

    jdbcTemplate.update(sql, param);
  }

  @Override
  public Optional<Item> findById(Long id) {
    String sql = "select id, item_name, price, quantity from item where id = :id";
    try {
      Map<String, Object> param = Map.of("id", id);
      Item item = jdbcTemplate.queryForObject(sql, param, itemRowMapper());
      return Optional.of(requireNonNull(item));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<Item> findAll(ItemSearchCond cond) {
    String itemName = cond.getItemName();
    Integer maxPrice = cond.getMaxPrice();

    SqlParameterSource param = new BeanPropertySqlParameterSource(cond);

    String sql = "select id, item_name, price, quantity from item";
    //동적 쿼리
    if (hasText(itemName) || maxPrice != null) {
      sql += " where";
    }

    boolean andFlag = false;
    if (hasText(itemName)) {
      sql += " item_name like concat('%', :itemName, '%')";
      andFlag = true;
    }

    if (maxPrice != null) {
      if (andFlag) {
        sql += " and";
      }
      sql += " price <= :maxPrice";
    }

    log.info("[dev] sql = {}", sql);
    return jdbcTemplate.query(sql, param, itemRowMapper());
  }

  private RowMapper<Item> itemRowMapper() {
    return BeanPropertyRowMapper.newInstance(Item.class); // camel 변환 지원
  }

}
