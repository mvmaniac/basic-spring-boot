package io.devfactory.example.jdbc.service;

import io.devfactory.example.jdbc.domain.Member;
import io.devfactory.example.jdbc.repository.MemberRepositoryV1;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {

  private final MemberRepositoryV1 memberRepository;

  public void accountTransfer(String fromId, String toId, int money) throws SQLException {
    Member fromMember = memberRepository.findById(fromId);
    Member toMember = memberRepository.findById(toId);

    memberRepository.update(fromId, fromMember.getMoney() - money);
    validation(toMember);
    memberRepository.update(toId, toMember.getMoney() + money);
  }

  private void validation(Member toMember) {
    if ("ex".equals(toMember.getMemberId())) {
      throw new IllegalStateException("이체 중 예외 발생");
    }
  }

}
