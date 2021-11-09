package io.devfactory.example.core.trace;

import lombok.Getter;

@SuppressWarnings("ClassCanBeRecord")
@Getter
public class TraceStatus {

  private final TraceId traceId;
  private final Long startTimeMs;
  private final String message;

  public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
    this.traceId = traceId;
    this.startTimeMs = startTimeMs;
    this.message = message;
  }

}
