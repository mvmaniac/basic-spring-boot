package io.devfactory.example.core.config.v3_proxy_factory.advice;

import io.devfactory.example.core.trace.TraceStatus;
import io.devfactory.example.core.trace.logtrace.LogTrace;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;

@SuppressWarnings("ClassCanBeRecord")
public class LogTraceAdvice implements MethodInterceptor {

  private final LogTrace logTrace;

  public LogTraceAdvice(LogTrace logTrace) {
    this.logTrace = logTrace;
  }

  @Override
  public Object invoke(@NonNull MethodInvocation invocation) throws Throwable {
    TraceStatus status = null;
    Object result;

    try {
      Method method = invocation.getMethod();
      String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
      status = logTrace.begin(message);

      // target 호출
      result = invocation.proceed();

      logTrace.end(status);

    } catch (Exception e) {
      logTrace.exception(status, e);
      throw e;
    }

    return result;
  }

}
