package com.usmb.bdgestback.payload.response;

public record LoginResponse(
        int id,
        String username,
        String email,
        String token
) { }
