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
