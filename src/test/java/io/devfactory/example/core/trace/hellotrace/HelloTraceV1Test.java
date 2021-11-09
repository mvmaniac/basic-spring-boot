package io.devfactory.example.core.trace.hellotrace;

import io.devfactory.example.core.trace.TraceStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("squid:S2699")
class HelloTraceV1Test {
  
  @DisplayName("시작 종료 테스트")
  @Test
  void begin_end() {
    HelloTraceV1 trace = new HelloTraceV1();
    TraceStatus status = trace.begin("hello");
    trace.end(status);
  }

  @DisplayName("예외 테스트")
  @Test
  void begin_exception() {
    HelloTraceV1 trace = new HelloTraceV1();
    TraceStatus status = trace.begin("hello");
    trace.exception(status, new IllegalStateException());
  }

}
