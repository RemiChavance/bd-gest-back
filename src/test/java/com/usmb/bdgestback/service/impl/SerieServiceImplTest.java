package com.usmb.bdgestback.service.impl;

import com.usmb.bdgestback.entity.Serie;
import com.usmb.bdgestback.entity.User;
import com.usmb.bdgestback.repository.SerieRepository;
import com.usmb.bdgestback.repository.UserRepository;
import com.usmb.bdgestback.service.inter.SerieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SerieServiceImplTest {

    private SerieService serieService;

    private SerieRepository serieRepository;
    private UserRepository userRepository;


    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        serieRepository = mock(SerieRepository.class);

        serieService = new SerieServiceImpl(serieRepository, userRepository);
    }

    @Test
    public void shouldReturnSerie() {
        int serieId = 1;
        when(serieRepository.findById(serieId)).thenReturn(Optional.of(new Serie()));
        assertThat(serieService.getById(serieId).isPresent()).isTrue();
    }

    @Test
    public void shouldNotFoundSerie() {
        int serieId = -1;
        when(serieRepository.findById(serieId)).thenReturn(Optional.empty());
        assertThat(serieService.getById(serieId).isPresent()).isFalse();
    }

    @Test
    public void shouldFailToFollowSerieUserNotFound() {
        int serieId = 1;
        int userId = -1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        when(serieRepository.findById(serieId)).thenReturn(Optional.of(new Serie()));
        assertThat(serieService.followSerie(serieId, userId)).isFalse();
    }

    @Test
    public void shouldFailToFollowSerieSerieNotFound() {
        int serieId = -1;
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(serieRepository.findById(serieId)).thenReturn(Optional.empty());
        assertThat(serieService.followSerie(serieId, userId)).isFalse();
    }

    @Test
    public void shouldReturnListOfBd() {
        String title = "tintin";
        when(serieRepository.findFirst2ByTitleContainingIgnoreCase(title)).thenReturn(Arrays.asList(new Serie(), new Serie()));
        assertThat(serieService.searchSerieByTitle(title).size() > 0).isTrue();
    }

    @Test
    public void shouldReturnListOfFollowedAuthors() {
        int userId = 1;
        User user = new User(1);
        user.setFollowedSeries(Arrays.asList(new Serie(), new Serie()));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThat(serieService.getFollowedSeries(userId).size() > 0).isTrue();
    }

    @Test
    public void shouldReturnNullIfUserNotFound() {
        int userId = -1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThat(serieService.getFollowedSeries(userId) == null).isTrue();
    }
}
