package io.devfactory.example.core.app.sample;

import io.devfactory.example.core.app.sample.annotation.Trace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SampleService {

  private final SampleRepository sampleRepository;

  @Trace
  public void request(String itemId) {
    sampleRepository.save(itemId);
  }

}
