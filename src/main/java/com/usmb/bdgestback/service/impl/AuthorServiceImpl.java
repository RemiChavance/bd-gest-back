package com.usmb.bdgestback.service.impl;

import com.usmb.bdgestback.entity.Author;
import com.usmb.bdgestback.entity.User;
import com.usmb.bdgestback.repository.AuthorRepository;
import com.usmb.bdgestback.repository.UserRepository;
import com.usmb.bdgestback.service.inter.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final UserRepository userRepository;

    @Override
    public Optional<Author> getById(int id) {
        return authorRepository.findById(id);
    }

    @Override
    public Boolean followAuthor(Integer authorId, int userId) {
        Optional<Author> author = this.getById(authorId);
        Optional<User> user = this.userRepository.findById(userId);

        if (author.isEmpty() || user.isEmpty()) {
            return false;
        } else {
            if (!user.get().getFollowedAuthors().contains(author.get())) {
                user.get().getFollowedAuthors().add(author.get());
                this.userRepository.save(user.get());
            }
            return true;
        }
    }

    @Override
    public List<Author> searchAuthorByName(String name) {
        return this.authorRepository.findFirst2ByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Author> getFollowedAuthors(int userId) {
        Optional<User> user = this.userRepository.findById(userId);
        if (user.isEmpty()) return null;
        return user.get().getFollowedAuthors();
    }
}
