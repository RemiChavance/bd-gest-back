package com.usmb.bdgestback.controller;

import com.usmb.bdgestback.payload.request.UserIdRequest;
import com.usmb.bdgestback.payload.response.*;
import com.usmb.bdgestback.service.inter.BdService;
import com.usmb.bdgestback.service.inter.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/collection")
public class CollectionController {

    public final CollectionService collectionService;
    public final BdService bdService;

    @GetMapping("/{userId}")
    public ResponseEntity<ManyBdCollectionResponse> getCollection(@PathVariable Integer userId) {
        List<OneBdCollectionResponse> collection = collectionService.getCollection(userId);
        if (collection == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new ManyBdCollectionResponse(collection));
    }

    @PostMapping("/{isbn}")
    public ResponseEntity<SuccessResponse> addBdToCollection(@PathVariable String isbn, @RequestBody UserIdRequest userIdRequest) {
        if (isbn.isEmpty() || userIdRequest == null || userIdRequest.userId() == 0) {
            return ResponseEntity.ok(new SuccessResponse(false));
        }

        return ResponseEntity.ok(
                new SuccessResponse(this.collectionService.addToCollection(isbn, userIdRequest.userId()))
        );
    }
}
