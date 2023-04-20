package com.usmb.bdgestback.controller;

import com.usmb.bdgestback.entity.Author;
import com.usmb.bdgestback.entity.Bd;
import com.usmb.bdgestback.payload.request.UserIdRequest;
import com.usmb.bdgestback.payload.response.AuthorResponse;
import com.usmb.bdgestback.payload.response.ManyAuthorResponse;
import com.usmb.bdgestback.payload.response.OneBdResponse;
import com.usmb.bdgestback.payload.response.SuccessResponse;
import com.usmb.bdgestback.service.inter.AuthorService;
import com.usmb.bdgestback.service.inter.BdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService authorService;
    private final BdService bdService;

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getOne(@PathVariable int id) {
        Optional<Author> author = this.authorService.getById(id);
        if (author.isEmpty()) return ResponseEntity.notFound().build();

        List<Bd> rawBds = this.bdService.getManyByAuthorId(author.get().getId());
        List<OneBdResponse> bds = new ArrayList<>();
        for (Bd bd: rawBds) {
            bds.add(this.bdService.toOneBdResponse(bd));
        }

        return ResponseEntity.ok(new AuthorResponse(author.get(), bds));
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<SuccessResponse> follow(@PathVariable Integer id, @RequestBody UserIdRequest userIdRequest) {
        if (id == 0 || userIdRequest == null || userIdRequest.userId() == 0) {
            return ResponseEntity.ok(new SuccessResponse(false));
        }

        return ResponseEntity.ok(
                new SuccessResponse(this.authorService.followAuthor(id, userIdRequest.userId()))
        );
    }


    @GetMapping("/followed/{userId}")
    public ResponseEntity<?> getFollowed(@PathVariable int userId) {
        List<Author> authors = this.authorService.getFollowedAuthors(userId);
        if (authors == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new ManyAuthorResponse(authors));
    }

}
