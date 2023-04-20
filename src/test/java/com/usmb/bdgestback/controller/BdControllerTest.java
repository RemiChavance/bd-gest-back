package com.usmb.bdgestback.controller;

import com.usmb.bdgestback.entity.Bd;
import com.usmb.bdgestback.service.inter.BdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class BdControllerTest {

    private BdService bdService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        bdService = mock(BdService.class);
        mockMvc = standaloneSetup(new BdController(bdService)).build();
    }


    @Test
    public void shouldReturnOneBd() throws Exception {
        when(bdService.getByIsbn("my-isbn")).thenReturn(Optional.of(new Bd()));

        mockMvc.perform(get("/api/bd/my-isbn"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFound() throws Exception {
        when(bdService.getByIsbn("unknown-isbn")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/bd/unknown-isbn"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnAllBds() throws Exception {
        when(bdService.getAll()).thenReturn(Arrays.asList(new Bd(), new Bd()));

        mockMvc.perform(get("/api/bd"))
                .andExpect(status().isOk());
    }
}
