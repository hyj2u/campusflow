package com.cnco.campusflow.point;

import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.store.StoreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserPointRepository extends JpaRepository<AppUserPointEntity, Long> {
    Optional<AppUserPointEntity> findByAppUserAndStoreAndEndTimestampIsNull(
        AppUserEntity appUser, 
        StoreEntity store
    );
    List<AppUserPointEntity> findByAppUserAndEndTimestampIsNull(AppUserEntity appUser);
    Page<AppUserPointEntity> findByAppUserAndStoreOrderByAppUserPointIdDesc(AppUserEntity appUser, StoreEntity store, Pageable pageable);
    Page<AppUserPointEntity> findByAppUserAndStoreAndTypeOrderByAppUserPointIdDesc(AppUserEntity appUser, StoreEntity store, String type, Pageable pageable);
} 