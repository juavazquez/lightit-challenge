package com.lightit.challenge.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncTasksConfig {
  private final Logger logger = LoggerFactory.getLogger(AsyncTasksConfig.class);

  @Bean
  public ThreadPoolTaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(2);
    executor.setMaxPoolSize(2);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("AsyncThread-");
    executor.setRejectedExecutionHandler(
        (r, executor1) -> logger.warn("Task rejected, thread pool is full and queue is also full"));
    executor.initialize();
    return executor;
  }
}
