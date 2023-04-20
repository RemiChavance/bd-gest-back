package com.usmb.bdgestback.controller;


import com.usmb.bdgestback.entity.User;
import com.usmb.bdgestback.payload.request.LoginRequest;
import com.usmb.bdgestback.payload.request.RegisterRequest;
import com.usmb.bdgestback.payload.response.LoginResponse;
import com.usmb.bdgestback.payload.response.SuccessResponse;
import com.usmb.bdgestback.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(new SuccessResponse(authService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteAccount(@PathVariable int id) {
        Optional<User> user = this.authService.getUserFromToken();
        if (user.isEmpty() || user.get().getId() != id) return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();

        boolean success = this.authService.deleteAccount(id);
        return ResponseEntity.ok(new SuccessResponse(success));
    }
}
