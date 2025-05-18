package com.cnco.campusflow.userconf;

import com.cnco.campusflow.user.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserconfNotiRepository extends JpaRepository<AppUserconfNotiEntity, Long> {
    Optional<AppUserconfNotiEntity> findByAppUser(AppUserEntity appUser);
} 