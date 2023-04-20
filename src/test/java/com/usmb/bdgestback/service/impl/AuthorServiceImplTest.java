package com.usmb.bdgestback.service.impl;

import com.usmb.bdgestback.entity.Author;
import com.usmb.bdgestback.entity.User;
import com.usmb.bdgestback.repository.AuthorRepository;
import com.usmb.bdgestback.repository.UserRepository;
import com.usmb.bdgestback.service.inter.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorServiceImplTest {
    private AuthorService authorService;

    private AuthorRepository authorRepository;
    private UserRepository userRepository;


    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        authorRepository = mock(AuthorRepository.class);

        authorService = new AuthorServiceImpl(authorRepository, userRepository);
    }

    @Test
    public void shouldReturnAuthor() {
        int authorId = 1;
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(new Author()));
        assertThat(authorService.getById(authorId).isPresent()).isTrue();
    }

    @Test
    public void shouldNotFoundSerie() {
        int authorId = -1;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());
        assertThat(authorService.getById(authorId).isPresent()).isFalse();
    }

    @Test
    public void shouldFailToFollowAuthorUserNotFound() {
        int authorId = 1;
        int userId = -1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(new Author()));
        assertThat(authorService.followAuthor(authorId, userId)).isFalse();
    }

    @Test
    public void shouldFailToFollowAuthorAuthorNotFound() {
        int authorId = -1;
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());
        assertThat(authorService.followAuthor(authorId, userId)).isFalse();
    }

    @Test
    public void shouldReturnListOfBd() {
        String name = "Hergé";
        when(authorRepository.findFirst2ByNameContainingIgnoreCase(name)).thenReturn(Arrays.asList(new Author(), new Author()));
        assertThat(authorService.searchAuthorByName(name).size() > 0).isTrue();
    }

    @Test
    public void shouldReturnListOfFollowedAuthors() {
        int userId = 1;
        User user = new User(1);
        user.setFollowedAuthors(Arrays.asList(new Author(), new Author()));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThat(authorService.getFollowedAuthors(userId).size() > 0).isTrue();
    }

    @Test
    public void shouldReturnNullIfUserNotFound() {
        int userId = -1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThat(authorService.getFollowedAuthors(userId) == null).isTrue();
    }
}
