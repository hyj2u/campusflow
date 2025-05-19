package com.cnco.campusflow.point;

import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.store.StoreEntity;
import com.cnco.campusflow.store.StoreRepository;
import com.cnco.campusflow.common.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

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
                .totalPoint(0)
                .currentPoint(0)
                .build());
    }

    @Transactional(readOnly = true)
    public List<AppUserPointEntity> getUserPointList(AppUserEntity appUser) {
        return appUserPointRepository.findByAppUserAndEndTimestampIsNull(appUser);
    }

    @Transactional
    public AppUserPointEntity usePoint(AppUserEntity appUser, StoreEntity store, Integer amount, String note) {
        // 현재 활성화된 포인트 내역 조회
        List<AppUserPointEntity> currentPointList = getUserPointList(appUser);
        if (currentPointList.isEmpty()) {
            throw new IllegalArgumentException("활성화된 포인트 내역이 없습니다.");
        }
        AppUserPointEntity currentPoint = currentPointList.get(0);

        // 잔액 체크
        if (currentPoint.getCurrentPoint() < amount) {
            throw new IllegalArgumentException("잔액이 부족합니다. (현재 잔액: " + currentPoint.getCurrentPoint() + "P, 필요 포인트: " + amount + "P)");
        }

        // 기존 포인트 내역 종료 처리
        currentPoint.setEndTimestamp(LocalDateTime.now());
        appUserPointRepository.save(currentPoint);

        // 새로운 포인트 내역 생성
        AppUserPointEntity newPoint = AppUserPointEntity.builder()
                .appUser(appUser)
                .store(store)
                .currentPoint(currentPoint.getCurrentPoint() - amount)
                .totalPoint(currentPoint.getTotalPoint())  // totalPoint는 유지
                .amount(-amount)  // 음수로 설정
                .type("USE")
                .note(note)
                .build();

        return appUserPointRepository.save(newPoint);
    }

    @Transactional
    public AppUserPointEntity earnPoint(AppUserEntity appUser, StoreEntity store, Integer amount, String note) {
        // 현재 활성화된 포인트 내역 조회
        List<AppUserPointEntity> currentPointList = getUserPointList(appUser);
        AppUserPointEntity currentPoint = currentPointList.isEmpty() ? 
            AppUserPointEntity.builder()
                .appUser(appUser)
                .currentPoint(0)
                .totalPoint(0)
                .build() : 
            currentPointList.get(0);

        // 기존 포인트 내역이 있으면 종료 처리
        if (currentPoint.getAppUserPointId() != null) {
            currentPoint.setEndTimestamp(LocalDateTime.now());
            appUserPointRepository.save(currentPoint);
        }

        // 새로운 포인트 내역 생성
        AppUserPointEntity newPoint = AppUserPointEntity.builder()
                .appUser(appUser)
                .store(store)
                .currentPoint(currentPoint.getCurrentPoint() + amount)
                .totalPoint(currentPoint.getTotalPoint() + amount)
                .amount(amount)  // 양수로 설정
                .type("EARN")
                .note(note)
                .build();

        return appUserPointRepository.save(newPoint);
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<AppUserPointEntity> getPointHistory(AppUserEntity appUser, StoreEntity store, String type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var pageResult = type != null && !type.isEmpty() ?
            appUserPointRepository.findByAppUserAndStoreAndTypeOrderByAppUserPointIdDesc(appUser, store, type, pageable) :
            appUserPointRepository.findByAppUserAndStoreOrderByAppUserPointIdDesc(appUser, store, pageable);
        return new PaginatedResponse<>(
            pageResult.getContent(),
            pageResult.getNumber(),
            pageResult.getSize(),
            pageResult.getTotalElements(),
            pageResult.getTotalPages()
        );
    }
} 