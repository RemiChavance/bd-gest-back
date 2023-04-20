package com.usmb.bdgestback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmb.bdgestback.payload.request.UserIdRequest;
import com.usmb.bdgestback.payload.response.OneBdCollectionResponse;
import com.usmb.bdgestback.service.inter.BdService;
import com.usmb.bdgestback.service.inter.CollectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CollectionControllerTest {
    public CollectionService collectionService;
    public BdService bdService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        collectionService = mock(CollectionService.class);
        bdService = mock(BdService.class);
        mockMvc = standaloneSetup(new CollectionController(collectionService, bdService)).build();
    }


    @Test
    public void shouldGetCollection() throws Exception {
        int userId = 1;
        List<OneBdCollectionResponse> collection = new ArrayList<>();

        when(collectionService.getCollection(userId)).thenReturn(collection);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/collection/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void shouldntGetCollectionBecauseOfBadUserId() throws Exception {
        int userId = -1;

        when(collectionService.getCollection(userId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/collection/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }


    @Test
    public void shouldAddBdToCollection() throws Exception {
        String isbn = "isbn";
        UserIdRequest userIdRequest = new UserIdRequest(1);

        when(collectionService.addToCollection(isbn, userIdRequest.userId())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/collection/" + isbn)
                        .content(asJsonString(userIdRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"success\": true}"));
    }

    @Test
    public void shouldntAddBdToCollection() throws Exception {
        String isbn = "unknown-isbn";
        UserIdRequest userIdRequest = new UserIdRequest(-1);

        when(collectionService.addToCollection(isbn, userIdRequest.userId())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/collection/" + isbn)
                        .content(asJsonString(userIdRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"success\": false}"));
    }





    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
