package io.devfactory.example.core.trace.logtrace;

import io.devfactory.example.core.trace.TraceStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("squid:S1186")
class FieldLogTraceTest {

  private final FieldLogTrace trace = new FieldLogTrace();

  @DisplayName("시작 종료 테스트")
  @Test
  void begin_end_level2() {
    TraceStatus status1 = trace.begin("hello1");
    TraceStatus status2 = trace.begin("hello2");
    trace.end(status2);
    trace.end(status1);
  }

  @DisplayName("예외 테스트")
  @Test
  void begin_exception_level2() {
    TraceStatus status1 = trace.begin("hello1");
    TraceStatus status2 = trace.begin("hello2");
    trace.exception(status2, new IllegalStateException());
    trace.exception(status1, new IllegalStateException());
  }

}
