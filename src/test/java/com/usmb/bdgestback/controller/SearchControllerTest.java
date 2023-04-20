package com.usmb.bdgestback.controller;

import com.usmb.bdgestback.service.inter.BdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class SearchControllerTest {
    private BdService bdService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        bdService = mock(BdService.class);
        mockMvc = standaloneSetup(new SearchController(bdService)).build();
    }

    @Test
    public void shouldSearchByTitle() throws Exception {
        when(bdService.searchBdByName("bd-title")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/search/title/bd-title"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSearchByISBN() throws Exception {
        when(bdService.searchBdByName("bd-isbn")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/search/isbn/bd-isbn"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSearchByAuthor() throws Exception {
        when(bdService.searchBdByName("bd-author")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/search/author/bd-author"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSearchBySerie() throws Exception {
        when(bdService.searchBdByName("bd-serie")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/search/serie/bd-serie"))
                .andExpect(status().isOk());
    }
}
