package com.lightit.challenge.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class AsyncTasksConfigTest {

  @Test
  public void testTaskExecutor() {
    AsyncTasksConfig config = new AsyncTasksConfig();
    ThreadPoolTaskExecutor executor = config.taskExecutor();

    assertNotNull(executor);
    assertEquals(2, executor.getCorePoolSize());
    assertEquals(2, executor.getMaxPoolSize());
    assertEquals(500, executor.getQueueCapacity());
    assertEquals("AsyncThread-", executor.getThreadNamePrefix());
  }
}
