package io.devfactory.example.core;

import io.devfactory.example.core.app.aspect.AspectV6Advice;
import io.devfactory.example.core.app.order.OrderRepository;
import io.devfactory.example.core.app.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("squid:S2699")
@Slf4j
//@Import(AspectV1.class)
//@Import(AspectV2.class)
//@Import(AspectV3.class)
//@Import(AspectV4PointCut.class)
//@Import({AspectV5Order.LogAspect.class, AspectV5Order.TransactionAspect.class})
@Import({AspectV6Advice.LogAspect.class, AspectV6Advice.TransactionAspect.class, AspectV6Advice.AdviceAspect.class})
@SpringBootTest
class AopTest {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderRepository orderRepository;

  @DisplayName("AOP Info")
  @Test
  void aopInfo() {
    log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
    log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
  }

  @DisplayName("성공")
  @Test
  void success() {
    orderService.orderItem("itemA");
  }

  @DisplayName("예외")
  @Test
  void exception() {
    assertThatThrownBy(() -> orderService.orderItem("ex"))
        .isInstanceOf(IllegalStateException.class);
  }

}
