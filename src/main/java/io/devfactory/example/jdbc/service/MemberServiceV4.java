package io.devfactory.example.jdbc.service;

import io.devfactory.example.jdbc.domain.Member;
import io.devfactory.example.jdbc.repository.MemberRepository;
import io.devfactory.example.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

// 예외 누수 문제 해결
// throws SQLException 제거
// MemberRepositoryV3 인터페이스 의존
@SuppressWarnings("squid:S101")
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV4 {

  private final MemberRepository memberRepository;

  @Transactional
  public void accountTransfer(String fromId, String toId, int money) {
    bizLogic(fromId, toId, money); // 비즈니스 로직
  }

  private void bizLogic(String fromId, String toId, int money) {
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
