package com.usmb.bdgestback.service.inter;

import com.usmb.bdgestback.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Optional<Author> getById(int id);
    Boolean followAuthor(Integer authorId, int userId);
    List<Author> searchAuthorByName(String name);
    List<Author> getFollowedAuthors(int userId);
}
