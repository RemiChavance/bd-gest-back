package com.usmb.bdgestback.service.impl;

import com.usmb.bdgestback.entity.Bd;
import com.usmb.bdgestback.entity.SharedBd;
import com.usmb.bdgestback.repository.SharedBdRepository;
import com.usmb.bdgestback.repository.UserRepository;
import com.usmb.bdgestback.service.inter.BdService;
import com.usmb.bdgestback.service.inter.SharedBdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SharedBdServiceImplTest {
    private SharedBdService sharedBdService;

    private SharedBdRepository sharedBdRepository;
    private UserRepository userRepository;
    private BdService bdService;


    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        bdService = mock(BdService.class);
        sharedBdRepository = mock(SharedBdRepository.class);

        sharedBdService = new SharedBdServiceImpl(sharedBdRepository, userRepository, bdService);
    }

    @Test
    public void shouldAddSharedBd() {
        String isbn = "isbn";
        int userId = 1;
        when(bdService.getByIsbn(isbn)).thenReturn(Optional.of(new Bd()));
        when(userRepository.existsById(userId)).thenReturn(true);

        assertThat(sharedBdService.addSharedBd(isbn, userId)).isTrue();
    }

    @Test
    public void shouldFailToAddSharedBdIsbnNotFound() {
        String isbn = "unknown-isbn";
        int userId = 1;
        when(bdService.getByIsbn(isbn)).thenReturn(Optional.empty());
        when(userRepository.existsById(userId)).thenReturn(true);

        assertThat(sharedBdService.addSharedBd(isbn, userId)).isFalse();
    }

    @Test
    public void shouldFailToAddSharedBdUserNotFound() {
        String isbn = "isbn";
        int userId = -1;
        when(bdService.getByIsbn(isbn)).thenReturn(Optional.of(new Bd()));
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThat(sharedBdService.addSharedBd(isbn, userId)).isFalse();
    }

    @Test
    public void shouldRemoveSharedBd() {
        String isbn = "isbn";
        int userId = 1;
        when(sharedBdRepository.findByIsbnAndUserId(isbn, userId)).thenReturn(Optional.of(new SharedBd()));
        assertThat(sharedBdService.removeSharedBd(isbn, userId)).isTrue();
    }

    @Test
    public void shouldFailToRemoveSharedBdNotFound() {
        String isbn = "unknown-isbn";
        int userId = 1;
        when(sharedBdRepository.findByIsbnAndUserId(isbn, userId)).thenReturn(Optional.empty());
        assertThat(sharedBdService.removeSharedBd(isbn, userId)).isFalse();
    }

    @Test
    public void shouldFindSharedBd() {
        String isbn = "isbn";
        int userId = 1;
        when(sharedBdRepository.existsByIsbnAndUserId(isbn, userId)).thenReturn(true);
        assertThat(sharedBdService.existsByIsbnAndUserId(isbn, userId)).isTrue();
    }

    @Test
    public void shouldNotFindSharedBd() {
        String isbn = "unknown-isbn";
        int userId = 1;
        when(sharedBdRepository.existsByIsbnAndUserId(isbn, userId)).thenReturn(false);
        assertThat(sharedBdService.existsByIsbnAndUserId(isbn, userId)).isFalse();
    }
}
