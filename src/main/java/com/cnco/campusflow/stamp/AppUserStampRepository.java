package com.cnco.campusflow.stamp;

import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.store.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AppUserStampRepository extends JpaRepository<AppUserStampEntity, Long> {
    Optional<AppUserStampEntity> findByAppUserAndStoreAndEndTimestampIsNull(
        AppUserEntity appUser, 
        StoreEntity store
    );
} 