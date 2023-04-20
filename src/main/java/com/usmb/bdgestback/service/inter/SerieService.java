package com.usmb.bdgestback.service.inter;

import com.usmb.bdgestback.entity.Serie;

import java.util.List;
import java.util.Optional;

public interface SerieService {
    Optional<Serie> getById(Integer serieId);
    Boolean followSerie(Integer serieId, int userId);
    List<Serie> searchSerieByTitle(String title);
    List<Serie> getFollowedSeries(int userId);
}
