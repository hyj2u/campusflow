package com.cnco.campusflow.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {

    boolean existsByUserId(String userId);
    boolean existsByNickname(String nickname);
    Optional<AppUserEntity> findByUserId(String userId);
}
