package com.usmb.bdgestback.service.impl;

import com.usmb.bdgestback.entity.Bd;
import com.usmb.bdgestback.entity.SharedBd;
import com.usmb.bdgestback.repository.SharedBdRepository;
import com.usmb.bdgestback.repository.UserRepository;
import com.usmb.bdgestback.service.inter.BdService;
import com.usmb.bdgestback.service.inter.SharedBdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SharedBdServiceImpl implements SharedBdService {

    private final SharedBdRepository sharedBdRepository;
    private final UserRepository userRepository;
    private final BdService bdService;

    @Override
    public boolean addSharedBd(String isbn, int userId) {
        Optional<Bd> bd = this.bdService.getByIsbn(isbn);
        if (bd.isEmpty() || !this.userRepository.existsById(userId)) return false;

        if (this.existsByIsbnAndUserId(isbn, userId)) return true;

        SharedBd sharedBd = SharedBd.builder()
                .isbn(isbn)
                .userId(userId)
                .date(new Date(System.currentTimeMillis()))
                .build();
        this.sharedBdRepository.save(sharedBd);
        return true;
    }

    @Override
    public boolean removeSharedBd(String isbn, int userId) {
        Optional<SharedBd> sharedBd = this.sharedBdRepository.findByIsbnAndUserId(isbn, userId);
        if (sharedBd.isEmpty()) return false;

        this.sharedBdRepository.delete(sharedBd.get());
        return true;
    }

    @Override
    public boolean existsByIsbnAndUserId(String isbn, int userId) {
        return this.sharedBdRepository.existsByIsbnAndUserId(isbn, userId);
    }
}
