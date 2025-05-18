package com.cnco.campusflow.stamp;

import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.store.StoreEntity;
import com.cnco.campusflow.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppUserStampService {
    
    private final AppUserStampRepository appUserStampRepository;
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public AppUserStampEntity getUserStamp(AppUserEntity appUser, Long storeId) {
        StoreEntity store = storeRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("Store not found with id: " + storeId));

        return appUserStampRepository.findByAppUserAndStoreAndEndTimestampIsNull(appUser, store)
            .orElse(AppUserStampEntity.builder()
                .appUser(appUser)
                .store(store)
                .totalStampCount(0)
                .currentStampCount(0)
                .build());
    }

    @Transactional
    public AppUserStampEntity useStamp(AppUserEntity appUser, Long storeId, Integer useStampCount, String note) {
        StoreEntity store = storeRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("Store not found with id: " + storeId));

        AppUserStampEntity currentStamp = appUserStampRepository.findByAppUserAndStoreAndEndTimestampIsNull(appUser, store)
            .orElseThrow(() -> new IllegalArgumentException("No active stamp found for this store"));

        if (currentStamp.getCurrentStampCount() < useStampCount) {
            throw new IllegalArgumentException("Not enough stamps. Current stamp count: " + currentStamp.getCurrentStampCount());
        }

        // 기존 스탬프 정보 종료 처리
        currentStamp.setEndTimestamp(LocalDateTime.now());
        appUserStampRepository.save(currentStamp);

        // 새로운 스탬프 정보 생성
        AppUserStampEntity newStamp = AppUserStampEntity.builder()
            .appUser(appUser)
            .store(store)
            .totalStampCount(currentStamp.getTotalStampCount())
            .currentStampCount(currentStamp.getCurrentStampCount() - useStampCount)
            .note(note)
            .build();

        return appUserStampRepository.save(newStamp);
    }

    @Transactional
    public AppUserStampEntity earnStamp(AppUserEntity appUser, Long storeId, Integer earnStampCount, String note) {
        StoreEntity store = storeRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("Store not found with id: " + storeId));

        AppUserStampEntity currentStamp = appUserStampRepository.findByAppUserAndStoreAndEndTimestampIsNull(appUser, store)
            .orElse(AppUserStampEntity.builder()
                .appUser(appUser)
                .store(store)
                .totalStampCount(0)
                .currentStampCount(0)
                .build());

        // 기존 스탬프 정보가 있으면 종료 처리
        if (currentStamp.getAppUserStampId() != null) {
            currentStamp.setEndTimestamp(LocalDateTime.now());
            appUserStampRepository.save(currentStamp);
        }

        // 새로운 스탬프 정보 생성
        AppUserStampEntity newStamp = AppUserStampEntity.builder()
            .appUser(appUser)
            .store(store)
            .totalStampCount(currentStamp.getTotalStampCount() + earnStampCount)
            .currentStampCount(currentStamp.getCurrentStampCount() + earnStampCount)
            .note(note)
            .build();

        return appUserStampRepository.save(newStamp);
    }
} 