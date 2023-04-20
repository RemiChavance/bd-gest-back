package com.usmb.bdgestback.controller;

import com.usmb.bdgestback.entity.Bd;
import com.usmb.bdgestback.payload.response.ManySearchBdResponse;
import com.usmb.bdgestback.payload.response.SearchBdResponse;
import com.usmb.bdgestback.service.inter.BdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final BdService bdService;

    @GetMapping("/title/{title}")
    public ResponseEntity<ManySearchBdResponse> searchByTitle(@PathVariable String title) {
        List<Bd> bds = this.bdService.searchBdByName(title);
        return ResponseEntity.ok(this.bdsToManySearchBdResponse(bds));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<ManySearchBdResponse> searchByISBN(@PathVariable String isbn) {
        List<Bd> bds = this.bdService.searchBdByISBN(isbn);
        return ResponseEntity.ok(this.bdsToManySearchBdResponse(bds));
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<ManySearchBdResponse> searchByAuthor(@PathVariable String author) {
        List<Bd> bds = this.bdService.searchBdByAuthor(author);
        return ResponseEntity.ok(this.bdsToManySearchBdResponse(bds));
    }

    @GetMapping("/serie/{serie}")
    public ResponseEntity<ManySearchBdResponse> searchBySerie(@PathVariable String serie) {
        List<Bd> bds = this.bdService.searchBdBySerie(serie);
        return ResponseEntity.ok(this.bdsToManySearchBdResponse(bds));
    }


    private ManySearchBdResponse bdsToManySearchBdResponse(List<Bd> bds) {
        List<SearchBdResponse> bdsResponse = new ArrayList<>();
        for (Bd bd: bds) {
            bdsResponse.add(this.bdToSearchBdResponse(bd));
        }
        return new ManySearchBdResponse(bdsResponse);
    }

    private SearchBdResponse bdToSearchBdResponse(Bd bd) {
        return new SearchBdResponse(bd.getTitle(), bd.getIsbn(), bd.getAuthorDraw(), bd.getAuthorScript(), bd.getSerie());
    }
}
