package io.devfactory.example.jdbc1.repository;

import io.devfactory.example.jdbc1.connection.DBConnectionUtil;
import io.devfactory.example.jdbc1.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

import static java.util.Objects.nonNull;

// JDBC - DriverManager 사용
@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection", "DuplicatedCode"})
@Slf4j
public class MemberRepositoryV0 {

  public Member save(Member member) throws SQLException {
    String sql = "insert into member(member_id, money) values(?, ?)";

    Connection con = null;
    PreparedStatement pstmt = null;

    try {
      con = getConnection();
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, member.getMemberId());
      pstmt.setInt(2, member.getMoney());
      pstmt.executeUpdate();

      return member;

    } catch (SQLException exception) {
      log.error("db error save", exception);
      throw exception;
    } finally {
      close(con, pstmt, null);
    }
  }

  public Member findById(String memberId) throws SQLException {
    String sql = "select * from member where member_id = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = getConnection();
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, memberId);

      rs = pstmt.executeQuery();
      if (!rs.next()) {
        throw new NoSuchElementException("member not found memberId = " + memberId);
      }

      return new Member(rs.getString("member_id"), rs.getInt("money"));

    } catch (SQLException exception) {
      log.error("db error findById", exception);
      throw exception;
    } finally {
      close(con, pstmt, rs);
    }
  }

  public void update(String memberId, int money) throws SQLException {
    String sql = "update member set money = ? where member_id = ?";

    Connection con = null;
    PreparedStatement pstmt = null;

    try {
      con = getConnection();
      pstmt = con.prepareStatement(sql);
      pstmt.setInt(1, money);
      pstmt.setString(2, memberId);
      pstmt.executeUpdate();

    } catch (SQLException exception) {
      log.error("db error update", exception);
      throw exception;
    } finally {
      close(con, pstmt, null);
    }
  }

  public void delete(String memberId) throws SQLException {
    String sql = "delete from member where member_id = ?";

    Connection con = null;
    PreparedStatement pstmt = null;

    try {
      con = getConnection();
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, memberId);
      pstmt.executeUpdate();

    } catch (SQLException exception) {
      log.error("db error delete", exception);
      throw exception;
    } finally {
      close(con, pstmt, null);
    }
  }

  private void close(Connection con, Statement stmt, ResultSet rs) {
    if (nonNull(rs)) {
      try {
        rs.close();
      } catch (SQLException e) {
        // nothing
      }
    }

    if (nonNull(stmt)) {
      try {
        stmt.close();
      } catch (SQLException e) {
        // nothing
      }
    }

    if (nonNull(con)) {
      try {
        con.close();
      } catch (SQLException e) {
        // nothing
      }
    }
  }

  private Connection getConnection() {
    return DBConnectionUtil.getConnection();
  }

}
