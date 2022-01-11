package io.devfactory.example.core.cglib.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@SuppressWarnings("ClassCanBeRecord")
@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

  private final Object target;

  public TimeMethodInterceptor(Object target) {
    this.target = target;
  }

  @Override
  public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
    log.info("TimeProxy 실행");

    long startTime = System.currentTimeMillis();
    Object result = proxy.invoke(target, args); // MethodProxy를 사용하는 것을 권장
    long endTime = System.currentTimeMillis();

    log.info("TimeProxy 종료 resultTime={}ms", endTime - startTime);
    return result;
  }

}
