package com.usmb.bdgestback.controller;

import com.usmb.bdgestback.entity.User;
import com.usmb.bdgestback.payload.request.SharedBdRequest;
import com.usmb.bdgestback.payload.response.SuccessResponse;
import com.usmb.bdgestback.service.impl.AuthenticationService;
import com.usmb.bdgestback.service.inter.SharedBdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sharedbd")
public class SharedBdController {

    private final SharedBdService sharedBdService;
    private final AuthenticationService authenticationService;

    @PostMapping()
    public ResponseEntity<?> addSharedBd(@RequestBody SharedBdRequest sharedBdRequest) {
        String isbn = sharedBdRequest.isbn();
        int userId = sharedBdRequest.userId();
        Optional<User> user = this.authenticationService.getUserFromToken();

        if (user.isEmpty() || user.get().getId() != userId) return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();
        if (isbn == null || isbn.isEmpty()) return ResponseEntity.notFound().build();

        boolean success = this.sharedBdService.addSharedBd(isbn, userId);

        if (!success) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new SuccessResponse(true));
    }

    @DeleteMapping()
    public ResponseEntity<?> removeSharedBd(@RequestBody SharedBdRequest sharedBdRequest) {
        String isbn = sharedBdRequest.isbn();
        int userId = sharedBdRequest.userId();
        Optional<User> user = this.authenticationService.getUserFromToken();

        if (user.isEmpty() || user.get().getId() != userId) return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();
        if (isbn == null || isbn.isEmpty()) return ResponseEntity.notFound().build();

        boolean success = this.sharedBdService.removeSharedBd(isbn, userId);

        if (!success) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new SuccessResponse(true));
    }
}
