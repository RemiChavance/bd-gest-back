package com.usmb.bdgestback.payload.request;

public record RegisterRequest(
        String username,
        String email,
        String password
) { }

