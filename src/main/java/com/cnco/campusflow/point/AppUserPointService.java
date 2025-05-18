package com.cnco.campusflow.point;

import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.store.StoreEntity;
import com.cnco.campusflow.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppUserPointService {
    
    private final AppUserPointRepository appUserPointRepository;
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public AppUserPointEntity getUserPoint(AppUserEntity appUser, Long storeId) {
        StoreEntity store = storeRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("Store not found with id: " + storeId));

        return appUserPointRepository.findByAppUserAndStoreAndEndTimestampIsNull(appUser, store)
            .orElse(AppUserPointEntity.builder()
                .appUser(appUser)
                .store(store)
                .totalPointCount(0)
                .currentPointCount(0)
                .build());
    }

    @Transactional
    public AppUserPointEntity usePoint(AppUserEntity appUser, Long storeId, Integer usePointCount, String note) {
        StoreEntity store = storeRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("Store not found with id: " + storeId));

        AppUserPointEntity currentPoint = appUserPointRepository.findByAppUserAndStoreAndEndTimestampIsNull(appUser, store)
            .orElseThrow(() -> new IllegalArgumentException("No active point found for this store"));

        if (currentPoint.getCurrentPointCount() < usePointCount) {
            throw new IllegalArgumentException("Not enough points. Current point count: " + currentPoint.getCurrentPointCount());
        }

        // 기존 포인트 정보 종료 처리
        currentPoint.setEndTimestamp(LocalDateTime.now());
        appUserPointRepository.save(currentPoint);

        // 새로운 포인트 정보 생성
        AppUserPointEntity newPoint = AppUserPointEntity.builder()
            .appUser(appUser)
            .store(store)
            .totalPointCount(currentPoint.getTotalPointCount())
            .currentPointCount(currentPoint.getCurrentPointCount() - usePointCount)
            .note(note)
            .build();

        return appUserPointRepository.save(newPoint);
    }

    @Transactional
    public AppUserPointEntity earnPoint(AppUserEntity appUser, Long storeId, Integer earnPointCount, String note) {
        StoreEntity store = storeRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("Store not found with id: " + storeId));

        AppUserPointEntity currentPoint = appUserPointRepository.findByAppUserAndStoreAndEndTimestampIsNull(appUser, store)
            .orElse(AppUserPointEntity.builder()
                .appUser(appUser)
                .store(store)
                .totalPointCount(0)
                .currentPointCount(0)
                .build());

        // 기존 포인트 정보가 있으면 종료 처리
        if (currentPoint.getAppUserPointId() != null) {
            currentPoint.setEndTimestamp(LocalDateTime.now());
            appUserPointRepository.save(currentPoint);
        }

        // 새로운 포인트 정보 생성
        AppUserPointEntity newPoint = AppUserPointEntity.builder()
            .appUser(appUser)
            .store(store)
            .totalPointCount(currentPoint.getTotalPointCount() + earnPointCount)
            .currentPointCount(currentPoint.getCurrentPointCount() + earnPointCount)
            .note(note)
            .build();

        return appUserPointRepository.save(newPoint);
    }
} 