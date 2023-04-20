package com.usmb.bdgestback.service.inter;

import com.usmb.bdgestback.payload.response.OneBdCollectionResponse;

import java.util.List;

public interface CollectionService {
    List<OneBdCollectionResponse> getCollection(Integer userId);
    Boolean addToCollection(String isbn, Integer userId);
}
