package io.devfactory.example.core.trace.logtrace;

import io.devfactory.example.core.trace.TraceStatus;

public interface LogTrace {

  TraceStatus begin(String message);

  void end(TraceStatus status);

  void exception(TraceStatus status, Exception exception);

}
