package io.devfactory.example.jdbc2.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@SuppressWarnings("com.haulmont.jpb.LombokDataInspection")
@Data
@Entity
public class Item {

  @GeneratedValue(strategy = IDENTITY)
  @Id
  private Long id;

  @Column(name = "item_name", length = 10)
  private String itemName;
  private Integer price;
  private Integer quantity;

  public Item() {
  }

  public Item(String itemName, Integer price, Integer quantity) {
    this.itemName = itemName;
    this.price = price;
    this.quantity = quantity;
  }

}
