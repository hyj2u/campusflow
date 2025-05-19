package com.cnco.campusflow.push;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 앱 사용자 푸시 정보 관리를 위한 레포지토리 인터페이스
 */
@Repository
public interface AppUserPushRepository extends JpaRepository<AppUserPushEntity, Long> {
    
    /**
     * 푸시 ID와 활성화 여부로 앱 사용자 푸시 목록을 조회합니다.
     */
    List<AppUserPushEntity> findByPush_PushIdAndActiveYn(Long pushId, Character activeYn);

    /**
     * 앱 사용자 ID와 활성화 여부로 앱 사용자 푸시 목록을 페이징 처리하여 조회합니다.
     */
    Page<AppUserPushEntity> findByAppUser_AppUserIdAndActiveYn(Long appUserId, Character activeYn, Pageable pageable);

    /**
     * 푸시 ID와 앱 사용자 ID로 앱 사용자 푸시 정보를 조회합니다.
     */
    boolean existsByPush_PushIdAndAppUser_AppUserId(Long pushId, Long appUserId);

    /**
     * 푸시 ID와 활성화 여부로 앱 사용자 푸시 목록을 페이징 처리하여 조회합니다.
     */
    Page<AppUserPushEntity> findByPush_PushIdAndActiveYn(Long pushId, Character activeYn, Pageable pageable);

    /**
     * 푸시 ID와 활성화 여부로 앱 사용자 푸시 목록을 페이징 처리하여 조회합니다.
     */
    Page<AppUserPushEntity> findByPush_PushId(Long pushId, Pageable pageable);
} 