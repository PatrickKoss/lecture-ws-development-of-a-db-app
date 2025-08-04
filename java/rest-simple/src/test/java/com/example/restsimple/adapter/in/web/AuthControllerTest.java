package com.example.restsimple.adapter.in.web;

import com.example.restsimple.application.port.in.*;
import com.example.restsimple.config.TestSecurityConfig;
import com.example.restsimple.config.JwtAuthenticationFilter;
import com.example.restsimple.config.SecurityConfig;
import com.example.restsimple.config.MetricsFilter;
import com.example.restsimple.domain.exception.AuthenticationException;
import com.example.restsimple.domain.exception.InvalidTokenException;
import com.example.restsimple.domain.model.Admin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = AuthController.class,
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MetricsFilter.class),
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class),
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
@Import(TestSecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterAdminUseCase registerAdminUseCase;

    @MockBean
    private LoginUseCase loginUseCase;

    @MockBean
    private RefreshTokenUseCase refreshTokenUseCase;

    @Test
    void register_WithValidData_ShouldReturnCreatedAdmin() throws Exception {
        // Given
        Admin createdAdmin = Admin.createNew("testuser", "John", "Doe", "test@example.com", "encoded-password");
        when(registerAdminUseCase.register(any(RegisterAdminCommand.class)))
                .thenReturn(createdAdmin);

        String requestBody = """
                {
                    "username": "testuser",
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "test@example.com",
                    "password": "password123"
                }
                """;

        // When & Then
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void register_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Given
        String requestBody = """
                {
                    "username": "",
                    "firstName": "",
                    "lastName": "",
                    "email": "invalid-email",
                    "password": ""
                }
                """;

        // When & Then
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_WithValidCredentials_ShouldReturnTokens() throws Exception {
        // Given
        LoginResponse loginResponse = new LoginResponse("access-token", "refresh-token", 900);
        when(loginUseCase.login(any(LoginCommand.class)))
                .thenReturn(loginResponse);

        String requestBody = """
                {
                    "username": "testuser",
                    "password": "password123"
                }
                """;

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("access-token"))
                .andExpect(jsonPath("$.refresh_token").value("refresh-token"))
                .andExpect(jsonPath("$.expires_in").value(900));
    }

    @Test
    void login_WithInvalidCredentials_ShouldReturnUnauthorized() throws Exception {
        // Given
        when(loginUseCase.login(any(LoginCommand.class)))
                .thenThrow(new AuthenticationException("Invalid username or password"));

        String requestBody = """
                {
                    "username": "testuser",
                    "password": "wrongpassword"
                }
                """;

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Login failed"));
    }

    @Test
    void refresh_WithValidToken_ShouldReturnNewTokens() throws Exception {
        // Given
        RefreshTokenResponse refreshResponse = new RefreshTokenResponse("new-access-token", 900);
        when(refreshTokenUseCase.refresh(any(RefreshTokenCommand.class)))
                .thenReturn(refreshResponse);

        String requestBody = """
                {
                    "refresh_token": "valid-refresh-token"
                }
                """;

        // When & Then
        mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("new-access-token"))
                .andExpect(jsonPath("$.expires_in").value(900));
    }

    @Test
    void refresh_WithInvalidToken_ShouldReturnUnauthorized() throws Exception {
        // Given
        when(refreshTokenUseCase.refresh(any(RefreshTokenCommand.class)))
                .thenThrow(new InvalidTokenException("Invalid refresh token"));

        String requestBody = """
                {
                    "refresh_token": "invalid-token"
                }
                """;

        // When & Then
        mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Token refresh failed"));
    }
}