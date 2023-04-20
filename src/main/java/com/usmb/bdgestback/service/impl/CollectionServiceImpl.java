package com.usmb.bdgestback.service.impl;

import com.usmb.bdgestback.entity.Bd;
import com.usmb.bdgestback.entity.User;
import com.usmb.bdgestback.payload.response.OneBdCollectionResponse;
import com.usmb.bdgestback.repository.UserRepository;
import com.usmb.bdgestback.service.inter.BdService;
import com.usmb.bdgestback.service.inter.CollectionService;
import com.usmb.bdgestback.service.inter.SharedBdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    public final UserRepository userRepository;
    public final BdService bdService;
    public final SharedBdService sharedBdService;

    @Override
    public List<OneBdCollectionResponse> getCollection(Integer userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        } else {
            List<Bd> bds = user.get().getCollection();
            List<OneBdCollectionResponse> collection = new ArrayList<>();

            for (Bd bd: bds) {
                OneBdCollectionResponse o = new OneBdCollectionResponse(
                  bd.getIsbn(),
                  bd.getTitle(),
                  bd.getImage(),
                  bd.getNumSerie(),
                  bd.getSerie().getId(),
                  bd.getAuthorScript(),
                  bd.getAuthorDraw(),
                  this.sharedBdService.existsByIsbnAndUserId(bd.getIsbn(), userId)
                );

                collection.add(o);
            }

            return collection;
        }
    }

    @Override
    public Boolean addToCollection(String isbn, Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Bd> bd = bdService.getByIsbn(isbn);

        if (bd.isEmpty() || user.isEmpty()) {
            return false;
        } else {
            if (!user.get().getCollection().contains(bd.get())) {
                user.get().getCollection().add(bd.get());
                this.userRepository.save(user.get());
            }
            return true;
        }
    }
}
