package io.devfactory.example.boot.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

public class HelloImportSelector implements ImportSelector {

  @NonNull
  @Override
  public String[] selectImports(@NonNull AnnotationMetadata importingClassMetadata) {
    return new String[]{"io.devfactory.example.boot.selector.HelloConfig"};
  }

}
