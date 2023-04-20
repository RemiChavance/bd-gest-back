package com.usmb.bdgestback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmb.bdgestback.entity.Serie;
import com.usmb.bdgestback.payload.request.UserIdRequest;
import com.usmb.bdgestback.service.inter.BdService;
import com.usmb.bdgestback.service.inter.SerieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


public class SerieControllerTest {

    private SerieService serieService;
    private BdService bdService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        bdService = mock(BdService.class);
        serieService = mock(SerieService.class);
        mockMvc = standaloneSetup(new SerieController(serieService, bdService)).build();
    }

    @Test
    public void shouldReturnOneSerie() throws Exception {
        when(serieService.getById(1)).thenReturn(Optional.of(new Serie(1)));

        mockMvc.perform(get("/api/serie/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFound() throws Exception {
        when(serieService.getById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/serie/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void shouldReturnFollowSuccess() throws Exception {
        UserIdRequest userIdRequest = new UserIdRequest(1);
        int serieId = 2;

        when(serieService.followSerie(serieId, userIdRequest.userId())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/serie/follow/" + serieId)
                        .content(asJsonString(userIdRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"success\": true}"));

    }

    @Test
    public void shouldReturnFollowFail() throws Exception {
        UserIdRequest userIdRequest = new UserIdRequest(1);
        int serieId = 2;

        when(serieService.followSerie(serieId, userIdRequest.userId())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/serie/follow/" + serieId)
                        .content(asJsonString(userIdRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"success\": false}"));
    }

    @Test
    public void shouldReturnFollowedSeries() throws Exception {
        int userId = 1;

        when(serieService.getFollowedSeries(userId)).thenReturn(Arrays.asList(new Serie(), new Serie()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/serie/followed/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFailToReturnFollowedSeriesBecauseUserNotFound() throws Exception {
        int userId = -1;

        when(serieService.getFollowedSeries(userId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/serie/followed/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
