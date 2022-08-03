package io.devfactory.example.tx.order;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class TxOrderServiceTest {

  @Autowired
  private TxOrderService txOrderService;
  @Autowired
  private TxOrderRepository txOrderRepository;

  @DisplayName("정상")
  @Test
  void complete() throws NotEnoughMoneyException {
    // given
    Order order = new Order();
    order.changeUsername("정상");

    // when
    txOrderService.order(order);

    // then
    Order findOrder = txOrderRepository.findById(order.getId()).orElseThrow();
    assertThat(findOrder.getPayStatus()).isEqualTo("완료");
  }

  @DisplayName("예외")
  @Test
  void runtimeException() {
    // given
    Order order = new Order();
    order.changeUsername("예외");

    // when
    Assertions.assertThatThrownBy(() -> txOrderService.order(order))
        .isInstanceOf(RuntimeException.class);

    // then
    Optional<Order> orderOptional = txOrderRepository.findById(order.getId());
    assertThat(orderOptional).isEmpty();
  }

  @DisplayName("잔고부족")
  @Test
  void bizException() {
    // given
    Order order = new Order();
    order.changeUsername("잔고부족");

    // when
    try {
      txOrderService.order(order);
    } catch (NotEnoughMoneyException e) {
      log.info("고객에게 잔고 부족을 알리고 별도의 계좌로 입금하도록 안내");
    }

    // then
    Order findOrder = txOrderRepository.findById(order.getId()).orElseThrow();
    assertThat(findOrder.getPayStatus()).isEqualTo("대기");
  }

}
