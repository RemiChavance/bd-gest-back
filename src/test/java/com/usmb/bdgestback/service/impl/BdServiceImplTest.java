package com.usmb.bdgestback.service.impl;

import com.usmb.bdgestback.entity.Bd;
import com.usmb.bdgestback.repository.BdRepository;
import com.usmb.bdgestback.service.inter.AuthorService;
import com.usmb.bdgestback.service.inter.BdService;
import com.usmb.bdgestback.service.inter.SerieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BdServiceImplTest {

    private BdService bdService;

    private BdRepository bdRepository;
    private AuthorService authorService;
    private SerieService serieService;


    @BeforeEach
    public void setup() {
        bdRepository = mock(BdRepository.class);
        authorService = mock(AuthorService.class);
        serieService = mock(SerieService.class);

        bdService = new BdServiceImpl(bdRepository, authorService, serieService);
    }


    @Test
    public void shouldReturnOneBd() {
        String isbn = "my-isbn";
        when(bdRepository.findOneByIsbn(isbn)).thenReturn(Optional.of(new Bd()));
        assertThat(bdService.getByIsbn(isbn).isPresent()).isTrue();
    }

    @Test
    public void shouldntReturnBd() {
        String isbn = "unknown-isbn";
        when(bdRepository.findOneByIsbn(isbn)).thenReturn(Optional.empty());
        assertThat(bdService.getByIsbn(isbn).isPresent()).isFalse();
    }

    @Test
    public void shouldReturnManyBd() {
        when(bdRepository.findAll()).thenReturn(Arrays.asList(new Bd(), new Bd()));
        assertThat(bdService.getAll().size() > 0).isTrue();
    }

    @Test
    public void shouldReturnManyBdBySerieId() {
        int serieId = 1;
        when(bdRepository.findAllBySerieId(serieId)).thenReturn(Arrays.asList(new Bd(), new Bd()));
        assertThat(bdService.getManyBySerieId(serieId).size() > 0).isTrue();
    }

    @Test
    public void shouldReturnZeroBdBySerieId() {
        // because serie id is unknown
        int serieId = -1;
        when(bdRepository.findAllBySerieId(serieId)).thenReturn(new ArrayList<>());
        assertThat(bdService.getManyBySerieId(serieId).size() == 0).isTrue();
    }

    @Test
    public void shouldReturnManyBdByTitle() {
        String title = "Tintin";
        when(bdRepository.findFirst5ByTitleContainingIgnoreCase(title)).thenReturn(Arrays.asList(new Bd(), new Bd()));
        assertThat(bdService.searchBdByName(title).size() > 0).isTrue();
    }

    @Test
    public void shouldReturnManyBdByISBN() {
        String isbn = "isbn";
        when(bdRepository.findFirst5ByIsbnContainingIgnoreCase(isbn)).thenReturn(Arrays.asList(new Bd(), new Bd()));
        assertThat(bdService.searchBdByISBN(isbn).size() > 0).isTrue();
    }
}
