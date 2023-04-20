package com.usmb.bdgestback.payload.response;

import com.usmb.bdgestback.entity.Author;

import java.util.List;

public record AuthorResponse(Author author, List<OneBdResponse> bds) {
}
