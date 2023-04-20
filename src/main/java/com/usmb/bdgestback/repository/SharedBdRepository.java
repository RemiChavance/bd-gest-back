package com.usmb.bdgestback.repository;

import com.usmb.bdgestback.entity.SharedBd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SharedBdRepository extends JpaRepository<SharedBd, Integer> {
    Optional<SharedBd> findByIsbnAndUserId(String isbn, int userId);
    boolean existsByIsbnAndUserId(String isbn, int userId);
}
