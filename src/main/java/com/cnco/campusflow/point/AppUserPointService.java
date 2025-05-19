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
            .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다. (매장 ID: " + storeId + ")"));

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
        if (usePointCount < 1) {
            throw new IllegalArgumentException("사용할 포인트는 1 이상이어야 합니다.");
        }

        StoreEntity store = storeRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다. (매장 ID: " + storeId + ")"));

        AppUserPointEntity currentPoint = appUserPointRepository.findByAppUserAndStoreAndEndTimestampIsNull(appUser, store)
            .orElseThrow(() -> new IllegalArgumentException("해당 매장의 활성화된 포인트 내역이 없습니다."));

        if (currentPoint.getCurrentPointCount() < usePointCount) {
            throw new IllegalArgumentException("포인트가 부족합니다. (현재 포인트: " + currentPoint.getCurrentPointCount() + ", 필요 포인트: " + usePointCount + ")");
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
        if (earnPointCount < 1) {
            throw new IllegalArgumentException("적립할 포인트는 1 이상이어야 합니다.");
        }

        StoreEntity store = storeRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다. (매장 ID: " + storeId + ")"));

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