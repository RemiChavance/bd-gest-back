package com.usmb.bdgestback.controller;

import com.usmb.bdgestback.entity.Bd;
import com.usmb.bdgestback.entity.Serie;
import com.usmb.bdgestback.payload.request.UserIdRequest;
import com.usmb.bdgestback.payload.response.*;
import com.usmb.bdgestback.service.inter.BdService;
import com.usmb.bdgestback.service.inter.SerieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/serie")
public class SerieController {

    private final SerieService serieService;
    private final BdService bdService;


    @GetMapping("/{id}")
    public ResponseEntity<SerieResponse> getOne(@PathVariable Integer id) {

        Optional<Serie> serie = this.serieService.getById(id);
        if (serie.isEmpty()) return ResponseEntity.notFound().build();

        List<Bd> bds = this.bdService.getManyBySerieId(serie.get().getId());
        List<OneBdResponse> manyBdResponse = new ArrayList<>();
        for (Bd bd: bds) {
            manyBdResponse.add(this.bdService.toOneBdResponse(bd));
        }

        return ResponseEntity.ok(new SerieResponse(serie.get(), manyBdResponse));
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<SuccessResponse> follow(@PathVariable Integer id, @RequestBody UserIdRequest userIdRequest) {
        if (id == 0 || userIdRequest == null || userIdRequest.userId() == 0) {
            return ResponseEntity.ok(new SuccessResponse(false));
        }

        return ResponseEntity.ok(
                new SuccessResponse(this.serieService.followSerie(id, userIdRequest.userId()))
        );
    }

    @GetMapping("/followed/{userId}")
    public ResponseEntity<?> getFollowed(@PathVariable int userId) {
        List<Serie> series = this.serieService.getFollowedSeries(userId);
        if (series == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new ManySerieResponse(series));
    }
}
