package io.devfactory.example.tx.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TxOrderRepository extends JpaRepository<Order, Long> {

}
