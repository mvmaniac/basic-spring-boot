package io.devfactory.example.core.app.proxy1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping// 스프링은 @Controller 또는 @ReuqestMapping이 있어야 스프링 컨트롤러로 인식
@ResponseBody
public interface ProxyOrderControllerV1 {

  @GetMapping("/proxy/v1/request")
  ResponseEntity<String> request(@RequestParam("itemId") String itemId);

  @GetMapping("/proxy/v1/no-log")
  ResponseEntity<String> noLog();

}
