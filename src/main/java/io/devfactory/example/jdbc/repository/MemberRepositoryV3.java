package io.devfactory.example.jdbc.repository;

import io.devfactory.example.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

// 트랜잭션 - 트랜잭션 매니저
// DataSourceUtils.getConnection()
// DataSourceUtils.releaseConnection()
@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection", "DuplicatedCode"})
@Slf4j
public class MemberRepositoryV3 {

  private final DataSource dataSource;

  public MemberRepositoryV3(DataSource dataSource) {
    this.dataSource = dataSource;
  }

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
    JdbcUtils.closeResultSet(rs);
    JdbcUtils.closeStatement(stmt);

    // 주의! 트랜잭션 동기화 사용하려면 DataSourceUtils를 사용해야 함
    DataSourceUtils.releaseConnection(con, dataSource);
  }

  private Connection getConnection() throws SQLException {
    // 주의! 트랜잭션 동기화 사용하려면 DataSourceUtils를 사용해야 함
    Connection connection = DataSourceUtils.getConnection(dataSource);
    log.info("get Connection={}, class={}", connection, connection.getClass());
    return connection;
  }

}
