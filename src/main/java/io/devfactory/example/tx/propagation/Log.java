package io.devfactory.example.tx.propagation;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "log")
@Entity
public class Log {

  @GeneratedValue
  @Id
  private Long id;

  private String message;

  public Log(String message) {
    this.message = message;
  }

}
