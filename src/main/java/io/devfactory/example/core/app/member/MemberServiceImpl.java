package io.devfactory.example.core.app.member;

import io.devfactory.example.core.app.member.annotation.ClassAop;
import io.devfactory.example.core.app.member.annotation.MethodAop;
import org.springframework.stereotype.Component;

@ClassAop
@Component
public class MemberServiceImpl implements MemberService {

  @MethodAop("test value")
  @Override
  public String hello(String param) {
    return "ok";
  }

  public String internal(String param) {
    return "ok";
  }

}
