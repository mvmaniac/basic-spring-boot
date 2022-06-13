package io.devfactory.example.jdbc1.exception.translator;

import io.devfactory.example.jdbc1.domain.Member;
import io.devfactory.example.jdbc1.repository.exception.MyDbException;
import io.devfactory.example.jdbc1.repository.exception.MyDuplicateKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import static io.devfactory.example.jdbc1.connection.ConnectionConst.*;

@Slf4j
class ExTranslatorV1Test {

  private Repository repository;
  private Service service;

  @BeforeEach
  void beforeEach() {
    DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
    Repository repository = new Repository(dataSource);
    service = new Service(repository);
  }

  @Test
  void duplicateKeySave() {
    service.create("myId");
    service.create("myId");
  }

  @Slf4j
  @RequiredArgsConstructor
  static class Service {

    private final Repository repository;

    public void create(String memberId) {
      try {
        repository.save(new Member(memberId, 0));
        log.info("savedId = {}", memberId);

      } catch (MyDuplicateKeyException e) {
        log.info("키 중복, 복구 시도");

        String retryId = generateNewId(memberId);
        log.info("retryId = {}", retryId);

        repository.save(new Member(retryId, 0));

      } catch (MyDbException e) {
        log.info("데이터 접근 계층 예외", e);
        throw e;
      }
    }

    private String generateNewId(String memberId) {
      return memberId + new Random().nextInt(10000);
    }

  }

  @SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
  @RequiredArgsConstructor
  static class Repository {

    private final DataSource dataSource;

    public Member save(Member member) {
      String sql = "insert into member(member_id, money) values(?, ?)";

      Connection con = null;
      PreparedStatement pstmt = null;

      try {
        con = dataSource.getConnection();
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, member.getMemberId());
        pstmt.setInt(2, member.getMoney());
        pstmt.executeUpdate();

        return member;

      } catch (SQLException e) {
        // h2 db
        if (e.getErrorCode() == 23505) {
          throw new MyDuplicateKeyException(e);
        }
        throw new MyDbException(e);

      } finally {
        JdbcUtils.closeStatement(pstmt);
        JdbcUtils.closeConnection(con);
      }
    }

  }

}
