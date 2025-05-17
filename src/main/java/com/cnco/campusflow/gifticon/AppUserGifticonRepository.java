package com.cnco.campusflow.gifticon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserGifticonRepository extends JpaRepository<AppUserGifticonEntity, Long>{
    
    // 앱 사용자 ID로 기프티콘 목록 조회
    Page<AppUserGifticonEntity> findByReceiver_AppUserIdAndActiveYn(Long appUserId, String activeYn, Pageable pageable);

    List<AppUserGifticonEntity> findByGifticon_GifticonIdAndActiveYn(Long gifticonId, String activeYn);

    Page<AppUserGifticonEntity> findByGifticon_GifticonIdAndActiveYn(Long gifticonId, String activeYn, Pageable pageable);

}