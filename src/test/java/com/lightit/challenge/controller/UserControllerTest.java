package com.lightit.challenge.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lightit.challenge.TestSecurityConfig;
import com.lightit.challenge.config.filters.SecretTokenFilter;
import com.lightit.challenge.dto.UserOutputDto;
import com.lightit.challenge.service.impl.UserDataService;

import jakarta.persistence.EntityNotFoundException;

@WebMvcTest(controllers = UserController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SecretTokenFilter.class))
@Import(TestSecurityConfig.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    UserDataService userDataService;

    private final String email = "example@email.com";

    @Test
    public void userRegistrationTest_OK() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.multipart("/api/users")
                        .file(new MockMultipartFile("documentImg", "test.jpg", "image/jpeg", new byte[] { 1, 2, 3, 4 }))
                        .param("email", "test@example.com")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("phoneNumber", "1234567890")
                        .param("streetLine", "123 Main St")
                        .param("city", "Anytown")
                        .param("state", "Anystate")
                        .param("country", "Anycountry")
                        .contentType("multipart/form-data"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void userRegistrationTest_BadRequest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.multipart("/api/users")
                        .contentType("multipart/form-data"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void userGetByEmailTest_OK() throws Exception {
        UserOutputDto userOutputDto = new UserOutputDto();
        when(userDataService.getUser(eq(email))).thenReturn(userOutputDto);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/users?email={email}", email))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(userOutputDto)));
    }

    @Test
    public void userGetByEmailTest_NotFound() throws Exception {
        when(userDataService.getUser(anyString())).thenThrow(new EntityNotFoundException());

        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/users?email={email}", email))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
