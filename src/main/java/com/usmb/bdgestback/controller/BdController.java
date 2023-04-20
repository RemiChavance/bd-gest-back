package com.usmb.bdgestback.controller;

import com.usmb.bdgestback.entity.Bd;
import com.usmb.bdgestback.payload.response.ManyBdResponse;
import com.usmb.bdgestback.payload.response.OneBdResponse;
import com.usmb.bdgestback.service.inter.BdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bd")
public class BdController {

    private final BdService bdService;

    @GetMapping()
    public ResponseEntity<ManyBdResponse> getAll() {
        List<OneBdResponse> b = new ArrayList<>();
        for (Bd bd: this.bdService.getAll()) {
            b.add(this.bdService.toOneBdResponse(bd));
        }

        ManyBdResponse manyBdResponse = new ManyBdResponse(b);
        return ResponseEntity.ok(manyBdResponse);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<OneBdResponse> getOne(@PathVariable String isbn) {

        Optional<Bd> bd = this.bdService.getByIsbn(isbn);

        if (bd.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(this.bdService.toOneBdResponse(bd.get()));
        }
    }
}