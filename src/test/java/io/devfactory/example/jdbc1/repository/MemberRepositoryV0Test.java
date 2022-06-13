package io.devfactory.example.jdbc1.repository;

import io.devfactory.example.jdbc1.domain.Member;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberRepositoryV0Test {

  private final MemberRepositoryV0 memberRepository = new MemberRepositoryV0();

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
