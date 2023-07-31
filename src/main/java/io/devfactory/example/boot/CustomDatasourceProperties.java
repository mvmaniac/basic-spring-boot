package io.devfactory.example.boot;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.validator.constraints.time.DurationMax;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.List;

// @Setter 대신에 생성자를 사용함, 생성자를 사용하면 final로 선언 가능...
@Getter
@Validated // 검증 가능하게
@ConfigurationProperties("custom.datasource")
public class CustomDatasourceProperties {

  @NotEmpty
  private final String url;
  @NotEmpty
  private final String username;

  private final String password;
  private final Etc etc;

  public CustomDatasourceProperties(String url, String username, String password, Etc etc) {
    this.url = url;
    this.username = username;
    this.password = password;
    this.etc = etc;
  }

  @Getter
  public static class Etc {

    @Min(1)
    @Max(999)
    private final int maxConnection;

    @DurationMin(seconds = 1)
    @DurationMax(seconds = 60)
    private final Duration timeout;

    private final List<String> options;

    public Etc(int maxConnection, Duration timeout, List<String> options) {
      this.maxConnection = maxConnection;
      this.timeout = timeout;
      this.options = options;
    }

  }

}
