package io.devfactory.example.boot.service;

import java.util.concurrent.atomic.AtomicInteger;

public interface MetricService {

  void order();

  void cancel();

  AtomicInteger getStock();

}
