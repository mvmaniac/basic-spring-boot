package io.devfactory.example.tx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class TxMemberRepository {

  private final EntityManager em;

  @Transactional
  public void save(Member member) {
    log.info("member 저장");
    em.persist(member);
  }

  public Optional<Member> find(String username) {
    return em.createQuery("select m from Member m where m.username = :username", Member.class)
        .setParameter("username", username)
        .getResultList().stream().findAny();
  }

}
