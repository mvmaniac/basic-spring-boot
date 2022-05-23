package io.devfactory.example.jdbc.service;

import io.devfactory.example.jdbc.domain.Member;
import io.devfactory.example.jdbc.repository.MemberRepositoryV1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import java.sql.SQLException;

import static io.devfactory.example.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// 기본 동작, 트랜잭션이 없어서 문제 발생
@Slf4j
class MemberServiceV1Test {

  public static final String MEMBER_A = "memberA";
  public static final String MEMBER_B = "memberB";
  public static final String MEMBER_EX = "ex";

  private MemberRepositoryV1 memberRepository;
  private MemberServiceV1 memberService;

  @BeforeEach
  void beforeEach() {
    DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
    memberRepository = new MemberRepositoryV1(dataSource);
    memberService = new MemberServiceV1(memberRepository);
  }

  @AfterEach
  void afterEach() throws SQLException {
    memberRepository.delete(MEMBER_A);
    memberRepository.delete(MEMBER_B);
    memberRepository.delete(MEMBER_EX);
  }

  @DisplayName("정상 이체 테스트")
  @Test
  void accountTransfer() throws Exception {
    // given
    Member memberA = new Member(MEMBER_A, 10000);
    Member memberB = new Member(MEMBER_B, 10000);
    memberRepository.save(memberA);
    memberRepository.save(memberB);

    // when
    memberService.accountTransfer(MEMBER_A, MEMBER_B, 2000);

    // then
    Member foundMemberA = memberRepository.findById(MEMBER_A);
    Member foundMemberB = memberRepository.findById(MEMBER_B);

    assertThat(foundMemberA.getMoney()).isEqualTo(8000);
    assertThat(foundMemberB.getMoney()).isEqualTo(12000);
  }

  @DisplayName("정상 이체 중 예외 발생 테스트")
  @Test
  void accountTransferEx() throws Exception {
    // given
    Member memberA = new Member(MEMBER_A, 10000);
    Member memberEx = new Member(MEMBER_EX, 10000);
    memberRepository.save(memberA);
    memberRepository.save(memberEx);

    // when
    assertThatThrownBy(() -> memberService.accountTransfer(MEMBER_A, MEMBER_EX, 2000))
        .isInstanceOf(IllegalStateException.class);

    // then
    Member foundMemberA = memberRepository.findById(MEMBER_A);
    Member foundMemberEx = memberRepository.findById(MEMBER_EX);

    // 트랜잭션이 적용이 안되어 있기 때문에 롤백이 되지 않아 금액이 차감됨
    assertThat(foundMemberA.getMoney()).isEqualTo(8000);
    assertThat(foundMemberEx.getMoney()).isEqualTo(10000);
  }

}
