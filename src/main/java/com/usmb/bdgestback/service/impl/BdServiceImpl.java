package com.usmb.bdgestback.service.impl;

import com.usmb.bdgestback.entity.Author;
import com.usmb.bdgestback.entity.Bd;
import com.usmb.bdgestback.entity.Serie;
import com.usmb.bdgestback.payload.response.OneBdResponse;
import com.usmb.bdgestback.repository.BdRepository;
import com.usmb.bdgestback.service.inter.AuthorService;
import com.usmb.bdgestback.service.inter.BdService;
import com.usmb.bdgestback.service.inter.SerieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BdServiceImpl implements BdService {

    private final BdRepository bdRepository;
    private final AuthorService authorService;
    private final SerieService serieService;

    @Override
    public List<Bd> getAll(){
        return bdRepository.findAll();
    }

    @Override
    public Optional<Bd> getByIsbn(String isbn) {
        return bdRepository.findOneByIsbn(isbn);
    }

    @Override 
    public OneBdResponse toOneBdResponse(Bd bd) {
        return new OneBdResponse(
                bd.getIsbn(),
                bd.getTitle(),
                bd.getImage(),
                bd.getNumSerie(),
                bd.getSerie().getId(),
                bd.getAuthorScript(),
                bd.getAuthorDraw()
        );
    }

    @Override
    public List<Bd> getManyBySerieId(int serieId) {
        return this.bdRepository.findAllBySerieId(serieId);
    }

    @Override
    public List<Bd> getManyByAuthorId(int authorId) {
        Optional<Author> author = authorService.getById(authorId);
        if (author.isEmpty()) {
            return null;
        } else {
            return this.getAllBdByAuthor(author.get());
        }
    }

    @Override
    public List<Bd> searchBdByName(String title) {
        return this.bdRepository.findFirst5ByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Bd> searchBdByISBN(String isbn) {
        return this.bdRepository.findFirst5ByIsbnContainingIgnoreCase(isbn);
    }

    @Override
    public List<Bd> searchBdByAuthor(String authorName) {
        List<Author> authors = this.authorService.searchAuthorByName(authorName);

        List<Bd> raw = new ArrayList<>();
        List<Bd> res = new ArrayList<>();

        // we get all bd from all author
        for (Author author: authors) {
            raw.addAll(this.getAllBdByAuthor(author));
        }

        // we remove double
        for (Bd bd: raw) {
            if (!res.contains(bd)) {
                res.add(bd);
                // we limit to 6 bd max
                if (res.size() == 6) break;
            }
        }

        return res;
    }

    @Override
    public List<Bd> searchBdBySerie(String title) {
        List<Serie> raw = this.serieService.searchSerieByTitle(title);
        List<Bd> res = new ArrayList<>();

        // for each serie found, we get the 3 first bd
        for (Serie serie: raw) {
            int i = 0;

            for (Bd bd: serie.getBds()) {
                res.add(bd);
                i++;
                if (i == 3) {
                    i = 0;
                    break;
                }

            }
        }

        return res;
    }


    private List<Bd> getAllBdByAuthor(Author author) {
        // we search for all BD draw or script by this author
        List<Bd> raw = this.bdRepository.findAllByAuthorDraw(author);
        raw.addAll(this.bdRepository.findAllByAuthorScript(author));

        List<Bd> res = new ArrayList<>();

        // we remove double because an author may have draw and script the same bd
        for (Bd bd: raw) {
            if (!res.contains(bd)) {
                res.add(bd);
            }
        }

        return res;
    }
}
























