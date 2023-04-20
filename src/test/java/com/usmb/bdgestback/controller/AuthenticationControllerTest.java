package com.usmb.bdgestback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmb.bdgestback.payload.request.LoginRequest;
import com.usmb.bdgestback.payload.request.RegisterRequest;
import com.usmb.bdgestback.payload.response.LoginResponse;
import com.usmb.bdgestback.service.impl.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class AuthenticationControllerTest {
    private AuthenticationService authService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        authService = mock(AuthenticationService.class);
        mockMvc = standaloneSetup(new AuthenticationController(authService)).build();
    }


    @Test
    public void shouldRegister() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("username", "email@mail.com", "123");

        when(authService.register(registerRequest)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/register")
                        .content(asJsonString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"success\": true}"));
    }


    @Test
    public void shouldntRegister() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("username", "username@mail.com", "123");

        when(authService.register(registerRequest)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/register")
                        .content(asJsonString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"success\": false}"));
    }


    @Test
    public void shouldLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest("username","123");
        LoginResponse expected = new LoginResponse(1, "username", "username@mail.com", "token");

        when(authService.login(loginRequest)).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .content(asJsonString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(expected)));
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
