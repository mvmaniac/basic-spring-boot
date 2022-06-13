package io.devfactory.example.jdbc1.repository;

import io.devfactory.example.jdbc1.domain.Member;

public interface MemberRepository {

  Member save(Member member);

  Member findById(String memberId);

  void update(String memberId, int money);

  void delete(String memberId);

}
