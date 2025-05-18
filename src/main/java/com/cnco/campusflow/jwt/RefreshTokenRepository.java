package com.cnco.campusflow.jwt;

import com.cnco.campusflow.user.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByAppUserAndDeviceInfo(AppUserEntity user, String deviceInfo);
    Optional<RefreshTokenEntity> findByToken(String refreshToken);
    List<RefreshTokenEntity> findByAppUser(AppUserEntity user);

}
