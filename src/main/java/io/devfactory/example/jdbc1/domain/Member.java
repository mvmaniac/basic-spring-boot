package io.devfactory.example.jdbc1.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Getter
public class Member {

  private String memberId;
  private int money;

  public Member(String memberId, int money) {
    this.memberId = memberId;
    this.money = money;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Member member)) return false;
    if (money != member.money) return false;
    return Objects.equals(memberId, member.memberId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(memberId, money);
  }

}
