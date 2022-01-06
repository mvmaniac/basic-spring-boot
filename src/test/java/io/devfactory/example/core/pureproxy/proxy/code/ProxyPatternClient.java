package io.devfactory.example.core.pureproxy.proxy.code;

@SuppressWarnings("ClassCanBeRecord")
public class ProxyPatternClient {

  private final Subject subject;

  public ProxyPatternClient(Subject subject) {
    this.subject = subject;
  }

  public void execute() {
    subject.operation();
  }

}
