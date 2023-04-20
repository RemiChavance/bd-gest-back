package com.usmb.bdgestback.service.inter;

public interface SharedBdService {

    boolean addSharedBd(String isbn, int userId);
    boolean removeSharedBd(String isbn, int userId);
    boolean existsByIsbnAndUserId(String isbn, int userId);
}
