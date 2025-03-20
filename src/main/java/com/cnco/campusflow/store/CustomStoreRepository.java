package com.cnco.campusflow.store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomStoreRepository {
     Page<StoreEntity> findStores(String search, Pageable pageable);
     Page<StoreListDto> findStoresNear(double latitude, double longitude, Pageable pageable);
}
