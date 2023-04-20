package com.usmb.bdgestback.service.inter;


import com.usmb.bdgestback.entity.Bd;
import com.usmb.bdgestback.payload.response.OneBdResponse;

import java.util.List;
import java.util.Optional;

public interface BdService {
    List<Bd> getAll();
    Optional<Bd> getByIsbn(String isbn);
    OneBdResponse toOneBdResponse(Bd bd);
    List<Bd> getManyBySerieId(int serieId);
    List<Bd> getManyByAuthorId(int authorId);
    List<Bd> searchBdByName(String title);
    List<Bd> searchBdByISBN(String isbn);
    List<Bd> searchBdByAuthor(String author);
    List<Bd> searchBdBySerie(String author);
}
