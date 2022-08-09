package io.devfactory.example.tx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@SuppressWarnings("squid:S112")
@Slf4j
@RequiredArgsConstructor
@Repository
public class TxLogRepository {

  private final EntityManager em;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void save(Log logMessage) {
    log.info("log 저장");
    em.persist(logMessage);

    if (logMessage.getMessage().contains("로그예외")) {
      log.info("log 저장시 예외 발생");
      throw new RuntimeException("예외 발생");
    }
  }

  public Optional<Log> find(String message) {
    return em.createQuery("select l from Log l where l.message = :message", Log.class)
        .setParameter("message", message)
        .getResultList().stream().findAny();
  }

}
