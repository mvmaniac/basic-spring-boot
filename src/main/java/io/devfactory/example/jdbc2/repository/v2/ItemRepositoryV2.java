package io.devfactory.example.jdbc2.repository.v2;

import io.devfactory.example.jdbc2.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepositoryV2 extends JpaRepository<Item, Long> {

}
