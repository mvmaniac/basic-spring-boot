package io.devfactory.example.core.trace.logtrace;

import io.devfactory.example.core.trace.TraceId;
import io.devfactory.example.core.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.isNull;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace {

  private static final String START_PREFIX = "-->";
  private static final String COMPLETE_PREFIX = "<--";
  private static final String EX_PREFIX = "<X-";

  private final ThreadLocal<TraceId> traceHolder = new ThreadLocal<>();

  @Override
  public TraceStatus begin(String message) {
    syncTraceId();

    TraceId traceId = traceHolder.get();
    Long startTimeMs = System.currentTimeMillis();
    log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
    return new TraceStatus(traceId, startTimeMs, message);
  }

  private void syncTraceId() {
    TraceId traceId = traceHolder.get();

    if (isNull(traceId)) {
      traceHolder.set(new TraceId());
      return;
    }

    traceHolder.set(traceId.createNextId());
  }

  @Override
  public void end(TraceStatus status) {
    complete(status, null);
  }

  @Override
  public void exception(TraceStatus status, Exception exception) {
    complete(status, exception);
  }

  @SuppressWarnings("DuplicatedCode")
  private void complete(TraceStatus status, Exception e) {
    Long stopTimeMs = System.currentTimeMillis();
    long resultTimeMs = stopTimeMs - status.getStartTimeMs();
    TraceId traceId = status.getTraceId();

    if (isNull(e)) {
      log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
    } else {
      log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
    }

    releaseTraceId();
  }

  private void releaseTraceId() {
    TraceId traceId = traceHolder.get();

    if (traceId.isFirstLevel()) {
      traceHolder.remove();
      return;
    }

    traceHolder.set(traceId.createPreviousId());
  }

  private static String addSpace(String prefix, int level) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < level; i += 1) {
      sb.append((i == level - 1) ? "|" + prefix : "|   ");
    }
    return sb.toString();
  }

}
