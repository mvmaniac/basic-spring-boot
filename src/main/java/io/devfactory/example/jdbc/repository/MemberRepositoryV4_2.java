package io.devfactory.example.jdbc.repository;

import io.devfactory.example.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

// SqlExceptionTranslator
@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection", "DuplicatedCode", "ConstantConditions", "squid:S101", "squid:S2259"})
@Slf4j
public class MemberRepositoryV4_2 implements MemberRepository {

  private final DataSource dataSource;
  private final SQLExceptionTranslator exceptionTranslator;

  public MemberRepositoryV4_2(DataSource dataSource) {
    this.dataSource = dataSource;
    this.exceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
  }

  @Override
  public Member save(Member member) {
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
      throw exceptionTranslator.translate("save", sql, exception);
    } finally {
      close(con, pstmt, null);
    }
  }

  @Override
  public Member findById(String memberId) {
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
      throw exceptionTranslator.translate("findById", sql, exception);
    } finally {
      close(con, pstmt, rs);
    }
  }

  @Override
  public void update(String memberId, int money) {
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
      throw exceptionTranslator.translate("update", sql, exception);
    } finally {
      close(con, pstmt, null);
    }
  }

  @Override
  public void delete(String memberId) {
    String sql = "delete from member where member_id = ?";

    Connection con = null;
    PreparedStatement pstmt = null;

    try {
      con = getConnection();
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, memberId);
      pstmt.executeUpdate();

    } catch (SQLException exception) {
      throw exceptionTranslator.translate("delete ", sql, exception);
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
