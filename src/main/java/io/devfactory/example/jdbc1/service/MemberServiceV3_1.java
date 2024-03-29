package io.devfactory.example.jdbc1.service;

import io.devfactory.example.jdbc1.domain.Member;
import io.devfactory.example.jdbc1.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;

// 트랜잭션 - 트랜잭션 매니저
@SuppressWarnings("squid:S101")
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV3_1 {

  private final PlatformTransactionManager transactionManager;
  private final MemberRepositoryV3 memberRepository;

  public void accountTransfer(String fromId, String toId, int money) {
    // 트랜잭션 시작
    TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

    try {
      bizLogic(fromId, toId, money); // 비즈니스 로직
      transactionManager.commit(status); // 성공시 커밋3
    } catch (Exception e) {
      transactionManager.rollback(status); // 실패 시 롤백
      throw new IllegalStateException(e);
    }
  }

  private void bizLogic(String fromId, String toId, int money) throws SQLException {
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
