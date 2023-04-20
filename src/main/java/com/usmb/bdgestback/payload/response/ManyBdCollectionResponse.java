package com.usmb.bdgestback.payload.response;

import java.util.List;

public record ManyBdCollectionResponse(List<OneBdCollectionResponse> bds) {
}
