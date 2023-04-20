package com.usmb.bdgestback.service.impl;

import com.usmb.bdgestback.entity.Serie;
import com.usmb.bdgestback.entity.User;
import com.usmb.bdgestback.repository.SerieRepository;
import com.usmb.bdgestback.repository.UserRepository;
import com.usmb.bdgestback.service.inter.SerieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SerieServiceImpl implements SerieService {
    private final SerieRepository serieRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<Serie> getById(Integer serieId) {
        return this.serieRepository.findById(serieId);
    }

    @Override
    public Boolean followSerie(Integer serieId, int userId) {
        Optional<Serie> serie = this.getById(serieId);
        Optional<User> user = this.userRepository.findById(userId);

        if (serie.isEmpty() || user.isEmpty()) {
            return false;
        } else {
            if (!user.get().getFollowedSeries().contains(serie.get())) {
                user.get().getFollowedSeries().add(serie.get());
                this.userRepository.save(user.get());
            }
            return true;
        }
    }

    @Override
    public List<Serie> searchSerieByTitle(String title) {
        return this.serieRepository.findFirst2ByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Serie> getFollowedSeries(int userId) {
        Optional<User> user = this.userRepository.findById(userId);
        if (user.isEmpty()) return null;
        return user.get().getFollowedSeries();
    }
}
