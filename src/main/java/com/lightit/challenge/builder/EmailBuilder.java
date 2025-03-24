package com.lightit.challenge.builder;

import java.util.HashMap;
import java.util.Map;

public class EmailBuilder {

  public static Map<String, String> buildWelcomeEmail(String firstName) {
    Map<String, String> email = new HashMap<>();
    email.put("subject", "Welcome to our platform");
    email.put("body", "Welcome to our platform, " + firstName + "!");
    return email;
  }
}
