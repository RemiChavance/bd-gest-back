package com.usmb.bdgestback.service.impl;

import com.usmb.bdgestback.config.JwtService;
import com.usmb.bdgestback.entity.Role;
import com.usmb.bdgestback.entity.User;
import com.usmb.bdgestback.payload.request.LoginRequest;
import com.usmb.bdgestback.payload.request.RegisterRequest;
import com.usmb.bdgestback.payload.response.LoginResponse;
import com.usmb.bdgestback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public boolean register(RegisterRequest request) {
        String username = request.username();
        String email = request.email();
        String password = request.password();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || repository.findByEmail(email).isPresent() || repository.findByUsername(username).isPresent()) {
            return false;
        }


        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build();

        repository.save(user);

        return true;
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        // if the function reach this point,
        // it means that the authentication works

        User user = repository.findByUsername(request.username()).orElseThrow();

        String jwtToken = jwtService.generatedToken(user);

        return new LoginResponse(user.getId(), user.getUsername(), user.getEmail(), jwtToken);
    }

    public boolean deleteAccount(int id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            repository.delete(user.get());
            return true;
        } else {
            return false;
        }
    }

    public Optional<User> getUserFromToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return Optional.empty();
        return repository.findById(((User)auth.getPrincipal()).getId());
    }
}
