package com.usmb.bdgestback.payload.response;

import com.usmb.bdgestback.entity.Author;

public record OneBdCollectionResponse(
        String isbn,
        String title,
        byte[] image,
        int numSerie,
        int serie,
        Author authorScript,
        Author authorDraw,
        boolean isShared
) { }
