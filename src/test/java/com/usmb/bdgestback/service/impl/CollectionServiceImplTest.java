package com.usmb.bdgestback.service.impl;

import com.usmb.bdgestback.entity.Bd;
import com.usmb.bdgestback.entity.Serie;
import com.usmb.bdgestback.entity.User;
import com.usmb.bdgestback.repository.UserRepository;
import com.usmb.bdgestback.service.inter.BdService;
import com.usmb.bdgestback.service.inter.CollectionService;
import com.usmb.bdgestback.service.inter.SharedBdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CollectionServiceImplTest {

    private CollectionService collectionService;

    private UserRepository userRepository;
    private BdService bdService;
    private SharedBdService sharedBdService;


    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        sharedBdService = mock(SharedBdService.class);
        bdService = mock(BdService.class);

        collectionService = new CollectionServiceImpl(userRepository, bdService, sharedBdService);
    }

    @Test
    public void shouldReturnCollection() {
        int userId = 1;
        User user = new User();
        Bd bd = new Bd();
        bd.setSerie(new Serie(1));
        user.setCollection(List.of(bd));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertThat(collectionService.getCollection(userId).size() > 0).isTrue();
    }

    @Test
    public void shouldFailToAddBdToCollectionUserNotFound() {
        String isbn = "isbn";
        int userId = -1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThat(collectionService.addToCollection(isbn, userId)).isFalse();
    }

    @Test
    public void shouldFailToAddBdToCollectionBdNotFound() {
        String isbn = "unknown-isbn";
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(bdService.getByIsbn(isbn)).thenReturn(Optional.empty());
        assertThat(collectionService.addToCollection(isbn, userId)).isFalse();
    }
}
