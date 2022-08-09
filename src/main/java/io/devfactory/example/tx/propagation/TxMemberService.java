package io.devfactory.example.tx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TxMemberService {

  private final TxMemberRepository memberRepository;
  private final TxLogRepository txLogRepository;

  @Transactional
  public void joinV1(String username) {
    Member member = new Member(username);
    Log logMessage = new Log(username);

    log.info("== memberRepository 호출 시작 ==");
    memberRepository.save(member);
    log.info("== memberRepository 호출 종료 ==");

    log.info("== logRepository 호출 시작 ==");
    txLogRepository.save(logMessage);
    log.info("== logRepository 호출 종료 ==");
  }

  @Transactional
  public void joinV2(String username) {
    Member member = new Member(username);
    Log logMessage = new Log(username);

    log.info("== memberRepository 호출 시작 ==");
    memberRepository.save(member);
    log.info("== memberRepository 호출 종료 ==");

    // try ~ catch로 예외를 잡더라고 rolback이 됨
    // txLogRepository.save에 예외가 발생하여 rollback-only가 있는 상태에서
    // 정상적으로 커밋이 되더라고 rollback-only가 있기 때문에
    // txLogRepository.save에서 트랜잭션 전파를 Propagation.REQUIRES_NEW로 설정하면 됨
    log.info("== logRepository 호출 시작 ==");
    try {
      txLogRepository.save(logMessage);
    } catch (RuntimeException e) {
      log.info("log 저장에 실패했습니다. logMessage={}", logMessage.getMessage());
      log.info("정상 흐름 반환");
    }

    log.info("== logRepository 호출 종료 ==");
  }

}
