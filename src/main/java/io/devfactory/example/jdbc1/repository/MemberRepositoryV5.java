package io.devfactory.example.jdbc1.repository;

import io.devfactory.example.jdbc1.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

// JdbcTemplate 사용
@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection", "DuplicatedCode", "squid:S101", "squid:S2259"})
@Slf4j
public class MemberRepositoryV5 implements MemberRepository {

  private final JdbcTemplate jdbcTemplate;

  public MemberRepositoryV5(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public Member save(Member member) {
    String sql = "insert into member(member_id, money) values(?, ?)";
    jdbcTemplate.update(sql, member.getMemberId(), member.getMoney());
    return member;
  }

  @Override
  public Member findById(String memberId) {
    String sql = "select * from member where member_id = ?";
    return jdbcTemplate.queryForObject(sql, memberRowMapper(), memberId);
  }

  private RowMapper<Member> memberRowMapper() {
    return (rs, rowNum) -> new Member(rs.getString("member_id"), rs.getInt("money"));
  }

  @Override
  public void update(String memberId, int money) {
    String sql = "update member set money = ? where member_id = ?";
    jdbcTemplate.update(sql, money, memberId);
  }

  @Override
  public void delete(String memberId) {
    String sql = "delete from member where member_id = ?";
    jdbcTemplate.update(sql, memberId);
  }

}
