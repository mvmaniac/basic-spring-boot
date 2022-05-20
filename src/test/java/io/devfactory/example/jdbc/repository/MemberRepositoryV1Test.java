package io.devfactory.example.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import io.devfactory.example.jdbc.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static io.devfactory.example.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberRepositoryV1Test {

  private MemberRepositoryV1 memberRepository;

  @BeforeEach
  void beforeEach() {
    // 기본 DriverManager - 항상 새로운 커넥션을 획득
    // DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

    // 커넥션 풀링
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(URL);
    dataSource.setUsername(USERNAME);
    dataSource.setPassword(PASSWORD);

    memberRepository = new MemberRepositoryV1(dataSource);
  }

  @Test
  void crud() throws SQLException {
    String memberId = "member0";

    // save
    Member member = new Member(memberId, 10000);
    memberRepository.save(member);

    // findById\
    Member foundMember = memberRepository.findById(memberId);
    assertThat(foundMember).isEqualTo(member).isNotSameAs(member);

    // update
    memberRepository.update(memberId, 20000);
    Member updatedMember = memberRepository.findById(memberId);
    assertThat(updatedMember.getMoney()).isEqualTo(20000);

    // delete
    memberRepository.delete(memberId);
    assertThatThrownBy(() -> memberRepository.findById(memberId))
        .isInstanceOf(NoSuchElementException.class);
  }

}
