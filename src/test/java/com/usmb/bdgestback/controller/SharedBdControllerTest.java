package com.usmb.bdgestback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmb.bdgestback.entity.User;
import com.usmb.bdgestback.payload.request.SharedBdRequest;
import com.usmb.bdgestback.service.impl.AuthenticationService;
import com.usmb.bdgestback.service.inter.SharedBdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class SharedBdControllerTest {

    private SharedBdService sharedBdService;
    private AuthenticationService authenticationService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        sharedBdService = mock(SharedBdService.class);
        authenticationService = mock(AuthenticationService.class);
        mockMvc = standaloneSetup(new SharedBdController(sharedBdService, authenticationService)).build();
    }


    @Test
    public void shouldAddSharedBd() throws Exception {
        SharedBdRequest sharedBdRequest = new SharedBdRequest("isbn", 1);

        when(this.sharedBdService.addSharedBd("isbn", 1)).thenReturn(true);
        when(this.authenticationService.getUserFromToken()).thenReturn(Optional.of(new User(1)));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/sharedbd")
                        .content(asJsonString(sharedBdRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"success\": true}"));
    }

    @Test
    public void shouldFailToAddSharedBdBecauseUserIdIsCustom() throws Exception {
        int realUserId = 1;
        int customUserId = 2;
        SharedBdRequest sharedBdRequest = new SharedBdRequest("isbn", customUserId);

        when(this.authenticationService.getUserFromToken()).thenReturn(Optional.of(new User(realUserId)));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/sharedbd")
                        .content(asJsonString(sharedBdRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldRemoveSharedBd() throws Exception {
        SharedBdRequest sharedBdRequest = new SharedBdRequest("isbn", 1);

        when(this.sharedBdService.removeSharedBd("isbn", 1)).thenReturn(true);
        when(this.authenticationService.getUserFromToken()).thenReturn(Optional.of(new User(1)));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/sharedbd")
                        .content(asJsonString(sharedBdRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"success\": true}"));
    }

    @Test
    public void shouldFailToRemoveSharedBdBecauseUserIdIsCustom() throws Exception {
        int realUserId = 1;
        int customUserId = 2;
        SharedBdRequest sharedBdRequest = new SharedBdRequest("isbn", customUserId);

        when(this.authenticationService.getUserFromToken()).thenReturn(Optional.of(new User(realUserId)));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/sharedbd")
                        .content(asJsonString(sharedBdRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
