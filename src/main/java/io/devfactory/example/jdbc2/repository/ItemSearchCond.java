package io.devfactory.example.jdbc2.repository;

import lombok.Data;

@Data
public class ItemSearchCond {

  private String itemName;
  private Integer maxPrice;

  public ItemSearchCond() {
  }

  public ItemSearchCond(String itemName, Integer maxPrice) {
    this.itemName = itemName;
    this.maxPrice = maxPrice;
  }

}
