package com.usmb.bdgestback.repository;

import com.usmb.bdgestback.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Integer> {
    List<Serie> findFirst2ByTitleContainingIgnoreCase(String title);
    Optional<Serie> findByTitle(String title); // don't use this except for database init !
}
