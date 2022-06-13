package io.devfactory.example.jdbc1.exception.translator;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static io.devfactory.example.jdbc1.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
@Slf4j
class SpringExceptionTranslatorTest {

  private DataSource dataSource;

  @BeforeEach
  void beforeEach() {
    dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
  }

  @Test
  void sqlExceptionErrorCode() {
    String sql = "select bad grammar";

    Connection con = null;
    PreparedStatement pstmt = null;

    try {
      con = dataSource.getConnection();
      pstmt = con.prepareStatement(sql);
      pstmt.executeQuery();

    } catch (SQLException e) {
      assertThat(e.getErrorCode()).isEqualTo(42122);

      int errorCode = e.getErrorCode();
      log.info("errorCode = {}", errorCode);
      log.info("error", e);

    } finally {
      JdbcUtils.closeStatement(pstmt);
      JdbcUtils.closeConnection(con);
    }
  }

  @Test
  void exceptionTranslator() {
    String sql = "select bad grammar";

    Connection con = null;
    PreparedStatement pstmt = null;

    try {
      con = dataSource.getConnection();
      pstmt = con.prepareStatement(sql);
      pstmt.executeQuery();

    } catch (SQLException e) {
      assertThat(e.getErrorCode()).isEqualTo(42122);

      SQLErrorCodeSQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator();
      DataAccessException resultEx = translator.translate("select", sql, e);

      log.info("resultEx", resultEx);

      assertThat(resultEx).isInstanceOf(BadSqlGrammarException.class);

    } finally {
      JdbcUtils.closeStatement(pstmt);
      JdbcUtils.closeConnection(con);
    }
  }


}
