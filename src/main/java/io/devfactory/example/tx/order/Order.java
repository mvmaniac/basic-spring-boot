package io.devfactory.example.tx.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "orders")
@Entity
public class Order {

  @GeneratedValue
  @Id
  private Long id;

  private String username; // 정상, 예외, 잔고부족
  private String payStatus; // 대기, 완료

  public void changeUsername(String username) {
    this.username = username;
  }

  public void changePayStatus(String payStatus) {
    this.payStatus = payStatus;
  }

}
