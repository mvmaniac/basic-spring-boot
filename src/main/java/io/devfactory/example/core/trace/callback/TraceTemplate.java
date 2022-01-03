package io.devfactory.example.core.trace.callback;

import io.devfactory.example.core.trace.TraceStatus;
import io.devfactory.example.core.trace.logtrace.LogTrace;

@SuppressWarnings("ClassCanBeRecord")
public class TraceTemplate {

  private final LogTrace trace;

  public TraceTemplate(LogTrace trace) {
    this.trace = trace;
  }

  public <T> T execute(String message, TraceCallback<T> callback) {
    TraceStatus status = null;

    try {
      status = trace.begin(message);

      // 로직 호출
      T result = callback.call();

      trace.end(status);
      return result;

    } catch (Exception e) {
      trace.exception(status, e);
      throw e; // 예외를 꼭 다시 던져주어야 함
    }
  }

}
