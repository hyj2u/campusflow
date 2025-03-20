package com.cnco.campusflow.store;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<StoreEntity, Long>, CustomStoreRepository {

}
