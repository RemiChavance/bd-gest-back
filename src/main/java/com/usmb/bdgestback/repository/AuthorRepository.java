package com.usmb.bdgestback.repository;

import com.usmb.bdgestback.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByName(String name);
    List<Author> findFirst2ByNameContainingIgnoreCase(String name);
}
