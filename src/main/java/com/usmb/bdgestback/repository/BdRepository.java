package com.usmb.bdgestback.repository;

import com.usmb.bdgestback.entity.Author;
import com.usmb.bdgestback.entity.Bd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BdRepository extends JpaRepository<Bd, String> {
    Optional<Bd> findOneByIsbn(String isbn);
    List<Bd> findAllBySerieId(int serieId);
    List<Bd> findAllByAuthorDraw(Author author);
    List<Bd> findAllByAuthorScript(Author author);
    List<Bd> findFirst5ByTitleContainingIgnoreCase(String title);
    List<Bd> findFirst5ByIsbnContainingIgnoreCase(String isbn);
}
