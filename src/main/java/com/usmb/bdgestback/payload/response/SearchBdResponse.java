package com.usmb.bdgestback.payload.response;

import com.usmb.bdgestback.entity.Author;
import com.usmb.bdgestback.entity.Serie;

public record SearchBdResponse(String title, String isbn, Author authorDraw, Author authorScript, Serie serie) {
}
