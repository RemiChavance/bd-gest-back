package com.usmb.bdgestback.payload.response;

import com.usmb.bdgestback.entity.Serie;

import java.util.List;

public record ManySerieResponse(List<Serie> series) {
}
