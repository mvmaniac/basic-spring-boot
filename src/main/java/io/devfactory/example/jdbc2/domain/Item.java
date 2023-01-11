package io.devfactory.example.jdbc2.domain;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

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
