package io.devfactory.example.tx.propagation;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
