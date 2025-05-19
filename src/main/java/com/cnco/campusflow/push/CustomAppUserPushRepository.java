package com.cnco.campusflow.push;

import com.cnco.campusflow.user.AppUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 앱 사용자 푸시 정보 관리를 위한 레포지토리 인터페이스
 */
@Repository
public interface CustomAppUserPushRepository  {

    Page<AppUserPushEntity> getPushList(AppUserEntity appUser, String sendType, Pageable pageable);
} 