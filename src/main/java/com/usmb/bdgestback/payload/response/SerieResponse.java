package com.usmb.bdgestback.payload.response;
import com.usmb.bdgestback.entity.Serie;

import java.util.List;

public record SerieResponse(Serie serie, List<OneBdResponse> bds) { }
