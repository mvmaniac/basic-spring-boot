package io.devfactory.example.tx.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("squid:S112")
@Slf4j
@RequiredArgsConstructor
@Service
public class TxOrderService {

  private final TxOrderRepository txOrderRepository;

  // JPA는 트랜잭션 커밋 시점에 Order 데이터를 DB에 반영한다.
  @Transactional
  public void order(Order order) throws NotEnoughMoneyException {
    log.info("order 호출");
    txOrderRepository.save(order);

    log.info("결제 프로제스 진입");
    if (order.getUsername().equals("예외")) {
      log.info("시스템 예외 발생");
      throw new RuntimeException("시스템 예외");

    } else if (order.getUsername().equals("잔고부족")) {
      // 상태 변경 후 커밋이 되어야 되기 떄문에 Exception으로 던짐
      log.info("잔고 부족 비즈니스 예외 발생");
      order.changePayStatus("대기");
      throw new NotEnoughMoneyException("잔고가 부족합니다"); 

    } else {
      // 정상 승인
      log.info("정상 승인");
      order.changePayStatus("완료");
    }

    log.info("결제 프로세스 완료");
  }
}
