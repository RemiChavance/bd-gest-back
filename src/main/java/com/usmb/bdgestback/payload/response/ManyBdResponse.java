package com.usmb.bdgestback.payload.response;

import java.util.List;

public record ManyBdResponse(List<OneBdResponse> bds) { }
