package io.devfactory.example.jdbc1.service;

import io.devfactory.example.jdbc1.domain.Member;
import io.devfactory.example.jdbc1.repository.MemberRepository;
import io.devfactory.example.jdbc1.repository.MemberRepositoryV5;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// 예외 누수 문제 해결
// throws SQLException 제거
// MemberRepositoryV3 인터페이스 의존
@Slf4j
@SpringBootTest
class MemberServiceV4Test {

  public static final String MEMBER_A = "memberA";
  public static final String MEMBER_B = "memberB";
  public static final String MEMBER_EX = "ex";

  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private MemberServiceV4 memberService;

  @TestConfiguration
  static class TestConfig {

    private final DataSource dataSource;

    TestConfig(DataSource dataSource) {
      this.dataSource = dataSource;
    }

    @Bean
    public MemberRepository memberRepository() {
      // return new MemberRepositoryV4_1(dataSource);
      // return new MemberRepositoryV4_2(dataSource);
      return new MemberRepositoryV5(dataSource);
    }

    @Bean
    public MemberServiceV4 memberServiceV4() {
      return new MemberServiceV4(memberRepository());
    }

  }

  @AfterEach
  void afterEach() {
    memberRepository.delete(MEMBER_A);
    memberRepository.delete(MEMBER_B);
    memberRepository.delete(MEMBER_EX);
  }

  @Test
  void aopCheck() {
    log.info("memberService class={}", memberService.getClass());
    log.info("memberRepository class={}", memberRepository.getClass());
    assertThat(AopUtils.isAopProxy(memberService)).isTrue();
    assertThat(AopUtils.isAopProxy(memberRepository)).isFalse();
  }

  @DisplayName("정상 이체 테스트")
  @Test
  void accountTransfer() {
    // given
    Member memberA = new Member(MEMBER_A, 10000);
    Member memberB = new Member(MEMBER_B, 10000);
    memberRepository.save(memberA);
    memberRepository.save(memberB);

    // when
    log.info("start tx...");
    memberService.accountTransfer(MEMBER_A, MEMBER_B, 2000);
    log.info("end tx...");

    // then
    Member foundMemberA = memberRepository.findById(MEMBER_A);
    Member foundMemberB = memberRepository.findById(MEMBER_B);

    assertThat(foundMemberA.getMoney()).isEqualTo(8000);
    assertThat(foundMemberB.getMoney()).isEqualTo(12000);
  }

  @DisplayName("정상 이체 중 예외 발생 테스트")
  @Test
  void accountTransferEx() {
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

    // 트랜잭션이 적용이 되었기 때문에 롤백이 되어 금액이 동일함
    assertThat(foundMemberA.getMoney()).isEqualTo(10000);
    assertThat(foundMemberEx.getMoney()).isEqualTo(10000);
  }

}
