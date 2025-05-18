package com.cnco.campusflow.money;

import com.cnco.campusflow.store.StoreEntity;
import com.cnco.campusflow.user.AppUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserMoneyRepository extends JpaRepository<AppUserMoneyEntity, Long> {
    
    List<AppUserMoneyEntity> findByAppUserAndEndTimestampIsNull(AppUserEntity appUser);
    
    List<AppUserMoneyEntity> findByAppUserAndStoreAndEndTimestampIsNull(AppUserEntity appUser, StoreEntity store);
    
    Page<AppUserMoneyEntity> findByAppUserOrderByAppUserMoneyIdDesc(AppUserEntity appUser, Pageable pageable);
    
    Page<AppUserMoneyEntity> findByAppUserAndTypeOrderByAppUserMoneyIdDesc(AppUserEntity appUser, String type, Pageable pageable);
} 