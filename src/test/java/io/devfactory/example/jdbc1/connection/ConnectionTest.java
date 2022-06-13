package io.devfactory.example.jdbc1.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import static io.devfactory.example.jdbc1.connection.ConnectionConst.*;

@Slf4j
class ConnectionTest {

  @DisplayName("DriverManager 테스트")
  @Test
  void driverManager() throws SQLException {
    Connection connection1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    Connection connection2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);

    log.info("connection={}, class={}", connection1, connection1.getClass());
    log.info("connection={}, class={}", connection2, connection2.getClass());
  }

  @DisplayName("DriverManagerDataSource 테스트")
  @Test
  void driverManagerDataSource() throws SQLException {
    // 항상 새로운 커넥션을 획득
    DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
    useDataSource(dataSource);
  }

  @DisplayName("HikariDataSource 테스트")
  @Test
  void dataSourceConnectionPool() throws SQLException, InterruptedException {
    // 커넥션 풀링
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(URL);
    dataSource.setUsername(USERNAME);
    dataSource.setPassword(PASSWORD);
    dataSource.setPoolName("MyPool");
    dataSource.setMaximumPoolSize(10); // 기본값 10임...

    useDataSource(dataSource);
    TimeUnit.SECONDS.sleep(1);
  }

  private void useDataSource(DataSource dataSource) throws SQLException {
    Connection connection1 = dataSource.getConnection();
    Connection connection2 = dataSource.getConnection();

    log.info("useDataSource - connection={}, class={}", connection1, connection1.getClass());
    log.info("useDataSource - connection={}, class={}", connection2, connection2.getClass());
  }

}
