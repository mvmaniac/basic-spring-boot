package io.devfactory.example.jdbc.repository;

import io.devfactory.example.jdbc.domain.Member;

public interface MemberRepository {

  Member save(Member member);

  Member findById(String memberId);

  void update(String memberId, int money);

  void delete(String memberId);

}
