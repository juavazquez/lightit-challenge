package com.lightit.challenge;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
  @GetMapping("/private-endpoint")
  public String privateEndpoint() {
    return "Access Granted";
  }
}
