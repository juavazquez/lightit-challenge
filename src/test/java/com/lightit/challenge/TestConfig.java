package com.lightit.challenge;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.PropertySource;

@TestConfiguration
@PropertySource("classpath:application-test.yml")
public class TestConfig {
}
