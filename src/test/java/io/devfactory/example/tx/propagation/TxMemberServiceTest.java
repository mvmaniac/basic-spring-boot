package io.devfactory.example.tx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
class TxMemberServiceTest {

  @Autowired
  private TxMemberService txMemberService;

  @Autowired
  private TxMemberRepository txMemberRepository;
  @Autowired
  private TxLogRepository txLogRepository;

  /**
   * memberService    @Transactional:OFF
   * memberRepository @Transactional:ON
   * logRepository    @Transactional:ON
   */
  @DisplayName("outerTxOff_success")
  @Test
  void outerTxOff_success() {
    // given
    String username = "outerTxOff_success";

    // when
    txMemberService.joinV1(username);

    // when: 모든 데이터가 정상 저장된다.
    assertThat(txMemberRepository.find(username)).isNotEmpty();
    assertThat(txLogRepository.find(username)).isNotEmpty();
  }

  /**
   * memberService    @Transactional:OFF
   * memberRepository @Transactional:ON
   * logRepository    @Transactional:ON Exception
   */
  @DisplayName("outerTxOff_fail")
  @Test
  void outerTxOff_fail() {
    // given
    String username = "로그예외_outerTxOff_fail";

    // when
    assertThatThrownBy(() -> txMemberService.joinV1(username))
        .isInstanceOf(RuntimeException.class);

    // when: log 데이터는 롤백된다.
    assertThat(txMemberRepository.find(username)).isNotEmpty();
    assertThat(txLogRepository.find(username)).isEmpty();
  }

  /**
   * memberService    @Transactional:ON
   * memberRepository @Transactional:OFF
   * logRepository    @Transactional:OFF
   */
  @DisplayName("singleTx")
  @Test
  void singleTx() {
    // given
    String username = "singleTx";

    // when
    txMemberService.joinV1(username);

    // when: 모든 데이터가 정상 저장된다.
    assertThat(txMemberRepository.find(username)).isNotEmpty();
    assertThat(txLogRepository.find(username)).isNotEmpty();
  }

  /**
   * memberService    @Transactional:ON
   * memberRepository @Transactional:ON
   * logRepository    @Transactional:ON
   */
  @DisplayName("outerTxOn_success")
  @Test
  void outerTxOn_success() {
    // given
    String username = "outerTxOn_success";

    // when
    txMemberService.joinV1(username);

    // when: 모든 데이터가 정상 저장된다.
    assertThat(txMemberRepository.find(username)).isNotEmpty();
    assertThat(txLogRepository.find(username)).isNotEmpty();
  }

  /**
   * memberService    @Transactional:ON
   * memberRepository @Transactional:ON
   * logRepository    @Transactional:ON Exception
   */
  @DisplayName("outerTxOn_fail")
  @Test
  void outerTxOn_fail() {
    // given
    String username = "로그예외_outerTxOn_fail";

    // when
    assertThatThrownBy(() -> txMemberService.joinV1(username))
        .isInstanceOf(RuntimeException.class);

    // when: 모든 데이터가 롤백된다.
    assertThat(txMemberRepository.find(username)).isEmpty();
    assertThat(txLogRepository.find(username)).isEmpty();
  }

  /**
   * memberService    @Transactional:ON
   * memberRepository @Transactional:ON
   * logRepository    @Transactional:ON Exception
   */
  @DisplayName("recoverException_fail")
  @Test
  void recoverException_fail() {
    // given
    String username = "로그예외_recoverException_fail";

    // when
    assertThatThrownBy(() -> txMemberService.joinV2(username))
        .isInstanceOf(UnexpectedRollbackException.class);

    // when: 모든 데이터가 롤백된다.
    assertThat(txMemberRepository.find(username)).isEmpty();
    assertThat(txLogRepository.find(username)).isEmpty();
  }

  /**
   * memberService    @Transactional:ON
   * memberRepository @Transactional:ON
   * logRepository    @Transactional:ON(REQUIRES_NEW) Exception
   */
  @DisplayName("recoverException_success")
  @Test
  void recoverException_success() {
    // given
    String username = "로그예외_recoverException_success";

    // when
    txMemberService.joinV2(username);

    // when: member 저장, log 롤백
    assertThat(txMemberRepository.find(username)).isNotEmpty();
    assertThat(txLogRepository.find(username)).isEmpty();
  }

}
