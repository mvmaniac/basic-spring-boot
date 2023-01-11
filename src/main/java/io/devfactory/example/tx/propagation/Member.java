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
@Table(name = "member")
@Entity
public class Member {

  @GeneratedValue
  @Id
  private Long id;

  private String username;

  public Member(String username) {
    this.username = username;
  }

}
