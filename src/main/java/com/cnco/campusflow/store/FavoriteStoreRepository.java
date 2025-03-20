package com.cnco.campusflow.store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteStoreRepository extends JpaRepository<FavoriteStoreEntity, Long>, CustomStoreRepository {

    void deleteByUserAppUserIdAndStoreStoreId(Long userAppUserId, Long storeId);
    Page<FavoriteStoreEntity> findFavoriteStoreEntitiesByUserAppUserId(Long userAppUserId, Pageable pageable);
}
