package io.devfactory.example.jdbc1.repository;

import io.devfactory.example.jdbc1.domain.Member;

import java.sql.SQLException;

// SQLException 과 같은 특정 구현 기술에 종속적인 체크 예외를 사용하게 되면 인터페이스에도 해당 예외를 포함해야 함
// 이것은 순수한 인터페이스가 아니며. JDBC 기술에 종속적인 인터페이스일 뿐임.
// 인터페이스를 만드는 목적은 구현체를 쉽게 변경하기 위함인데, 이미 인터페이스가 특정 구현 기술에 오염이 되어 버림
// 다만 최상위인 Exception으로 던지는 것은 허용
public interface MemberRepositoryEx {

  Member save(Member member) throws SQLException;

  Member findById(String memberId) throws SQLException;

  void update(String memberId, int money) throws SQLException;

  void delete(String memberId) throws SQLException;

}
