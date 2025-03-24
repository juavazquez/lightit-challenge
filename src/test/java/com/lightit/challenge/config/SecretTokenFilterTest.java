package com.lightit.challenge.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lightit.challenge.TestController;
import com.lightit.challenge.config.filters.SecretTokenFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith(MockitoExtension.class)
public class SecretTokenFilterTest {

  @Autowired MockMvc mockMvc;

  SecretTokenFilter secretTokenFilter = new SecretTokenFilter("token123");

  @BeforeEach
  public void setup() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(new TestController())
            .addFilter(secretTokenFilter)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .build();
  }

  @Test
  public void test() throws Exception {
    mockMvc
        .perform(get("/api/private-endpoint").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }
}
