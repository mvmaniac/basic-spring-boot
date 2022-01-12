package io.devfactory.example.core.advisor;

import io.devfactory.example.core.common.advice.TimeAdvice;
import io.devfactory.example.core.common.service.ServiceImpl;
import io.devfactory.example.core.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;

@SuppressWarnings("squid:S2699")
@Slf4j
class AdvisorTest {

  @DisplayName("Advisor 테스트 1")
  @Test
  void advisorTest1() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);

    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
    proxyFactory.addAdvisor(advisor);

    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    proxy.save();
    proxy.find();
  }

  @DisplayName("직접 만든 포인트 컷")
  @Test
  void advisorTest2() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);

    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointCut(), new TimeAdvice());
    proxyFactory.addAdvisor(advisor);

    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    proxy.save();
    proxy.find();
  }

  static class MyPointCut implements Pointcut {

    @Override
    public @NonNull ClassFilter getClassFilter() {
      return ClassFilter.TRUE;
    }

    @Override
    public @NonNull MethodMatcher getMethodMatcher() {
      return new MyMethodMatcher();
    }

  }

  static class MyMethodMatcher implements MethodMatcher {

    @Override
    public boolean matches(Method method, @NonNull Class<?> targetClass) {
      boolean result = "save".equals(method.getName());
      log.info("포인트 컷 호출 method={}, targetClass={}", method.getName(), targetClass);
      log.info("포인트 컷 결과 result={}", result);
      return result;
    }

    @Override
    public boolean isRuntime() {
      return false;
    }

    @Override
    public boolean matches(@NonNull Method method, @NonNull Class<?> targetClass,
        @NonNull Object... args) {
      return false;
    }

  }

  @DisplayName("스프링이 제공하는 포인트 컷")
  @Test
  void advisorTest3() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);

    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    pointcut.setMappedName("save");

    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
    proxyFactory.addAdvisor(advisor);

    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    proxy.save();
    proxy.find();
  }

}
