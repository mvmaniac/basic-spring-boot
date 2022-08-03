package io.devfactory.example.tx.order;

public class NotEnoughMoneyException extends Exception {

  public NotEnoughMoneyException(String message) {
    super(message);
  }

}
