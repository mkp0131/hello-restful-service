package kr.lililli.hellorestfulservice.controller;


import java.util.Locale;
import kr.lililli.hellorestfulservice.bean.HelloBean;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  private final MessageSource messageSource;

  public HelloController(MessageSource messageSource) {
    this.messageSource = messageSource;
  }


  @GetMapping("/")
  public String getHome(
      @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
    return messageSource.getMessage("greeting.message", null, locale);

  }

  @GetMapping("/hello-bean/{name}")
  public HelloBean getHelloBean(@PathVariable String name) {
    return new HelloBean("Hello, Bean! " + name);
  }


}
