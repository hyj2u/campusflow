package com.cnco.campusflow.store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomStoreRepository {
     Page<StoreListDto> findStores(String search,double latitude, double longitude, Pageable pageable);

}
