package io.devfactory.example.core.pureproxy.concreteproxy;

@SuppressWarnings("ClassCanBeRecord")
public class ConcreteClient {

  private final ConcreteLogic concreteLogic;

  public ConcreteClient(ConcreteLogic concreteLogic) {
    this.concreteLogic = concreteLogic;
  }

  public void execute() {
    concreteLogic.operation();
  }

}
