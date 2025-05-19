package com.cnco.campusflow.money;

import com.cnco.campusflow.store.StoreEntity;
import com.cnco.campusflow.store.StoreRepository;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserMoneyService {

    private final AppUserMoneyRepository appUserMoneyRepository;
    private final AppUserRepository appUserRepository;
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public AppUserMoneyEntity getUserMoney(AppUserEntity appUser) {
        List<AppUserMoneyEntity> currentMoneyList = appUserMoneyRepository.findByAppUserAndEndTimestampIsNull(appUser);
        if (currentMoneyList.isEmpty()) {
            throw new IllegalArgumentException("활성화된 머니 내역이 없습니다.");
        }
        return currentMoneyList.get(0);
    }

    @Transactional(readOnly = true)
    public List<AppUserMoneyEntity> getUserMoneyList(AppUserEntity appUser) {
        return appUserMoneyRepository.findByAppUserAndEndTimestampIsNull(appUser);
    }

    @Transactional
    public AppUserMoneyEntity earnMoney(AppUserEntity appUser, StoreEntity store, Integer amount, String note) {
        // 현재 활성화된 머니 내역 조회
        List<AppUserMoneyEntity> currentMoneyList = getUserMoneyList(appUser);
        AppUserMoneyEntity currentMoney = currentMoneyList.isEmpty() ? 
            AppUserMoneyEntity.builder()
                .appUser(appUser)
                .currentMoney(0)
                .totalMoney(0)
                .build() : 
            currentMoneyList.get(0);

        // 기존 머니 내역이 있으면 종료 처리
        if (currentMoney.getAppUserMoneyId() != null) {
            currentMoney.setEndTimestamp(LocalDateTime.now());
            appUserMoneyRepository.save(currentMoney);
        }

        // 새로운 머니 내역 생성
        AppUserMoneyEntity newMoney = AppUserMoneyEntity.builder()
                .appUser(appUser)
                .store(store)
                .currentMoney(currentMoney.getCurrentMoney() + amount)
                .totalMoney(currentMoney.getTotalMoney() + amount)
                .type("EARN")
                .note(note)
                .build();

        return appUserMoneyRepository.save(newMoney);
    }

    @Transactional
    public AppUserMoneyEntity chargeMoney(AppUserEntity appUser, StoreEntity store, Integer amount, String note) {
        return null;
    }

    @Transactional
    public AppUserMoneyEntity useMoney(AppUserEntity appUser, StoreEntity store, Integer amount, String note) {
        // 현재 활성화된 머니 내역 조회
        List<AppUserMoneyEntity> currentMoneyList = getUserMoneyList(appUser);
        if (currentMoneyList.isEmpty()) {
            throw new IllegalArgumentException("활성화된 머니 내역이 없습니다.");
        }
        AppUserMoneyEntity currentMoney = currentMoneyList.get(0);

        // 잔액 체크
        if (currentMoney.getCurrentMoney() < amount) {
            throw new IllegalArgumentException("잔액이 부족합니다. (현재 잔액: " + currentMoney.getCurrentMoney() + "원, 필요 금액: " + amount + "원)");
        }

        // 기존 머니 내역 종료 처리
        currentMoney.setEndTimestamp(LocalDateTime.now());
        appUserMoneyRepository.save(currentMoney);

        // 새로운 머니 내역 생성
        AppUserMoneyEntity newMoney = AppUserMoneyEntity.builder()
                .appUser(appUser)
                .store(store)
                .currentMoney(currentMoney.getCurrentMoney() - amount)
                .totalMoney(currentMoney.getTotalMoney())  // totalMoney는 유지
                .type("USE")
                .note(note)
                .build();

        return appUserMoneyRepository.save(newMoney);
    }

    @Transactional
    public AppUserMoneyEntity giftMoney(AppUserEntity sender, AppUserEntity receiver, Long amount, String note) {
        // 자기 자신에게 선물하는 경우 체크
        if (sender.getAppUserId().equals(receiver.getAppUserId())) {
            throw new IllegalArgumentException("자기 자신에게는 머니를 선물할 수 없습니다.");
        }

        // Get sender's current money record
        List<AppUserMoneyEntity> senderMoneyList = getUserMoneyList(sender);
        if (senderMoneyList.isEmpty()) {
            throw new IllegalArgumentException("보내는 사람의 머니 내역이 없습니다.");
        }
        AppUserMoneyEntity senderMoney = senderMoneyList.get(0);
        
        if (senderMoney.getCurrentMoney() < amount.intValue()) {
            throw new IllegalArgumentException("잔액이 부족합니다. (현재 잔액: " + senderMoney.getCurrentMoney() + "원, 선물 금액: " + amount + "원)");
        }

        // End sender's current record
        senderMoney.setEndTimestamp(LocalDateTime.now());
        appUserMoneyRepository.save(senderMoney);

        // Create new record for sender with reduced balance
        AppUserMoneyEntity newSenderMoney = AppUserMoneyEntity.builder()
                .appUser(sender)
                .currentMoney(senderMoney.getCurrentMoney() - amount.intValue())
                .totalMoney(senderMoney.getTotalMoney())  // totalMoney 유지
                .type("GIFT")
                .note(note != null ? note + " (to: " + receiver.getNickname() + ")" : "머니 선물 보냄 (to: " + receiver.getNickname() + ")")
                .build();
        appUserMoneyRepository.save(newSenderMoney);

        // Get receiver's current money record
        List<AppUserMoneyEntity> receiverMoneyList = getUserMoneyList(receiver);
        AppUserMoneyEntity receiverMoney = receiverMoneyList.isEmpty() ? 
            AppUserMoneyEntity.builder()
                .appUser(receiver)
                .currentMoney(0)
                .totalMoney(0)
                .build() : 
            receiverMoneyList.get(0);

        // End receiver's current record if exists
        if (receiverMoney.getAppUserMoneyId() != null) {
            receiverMoney.setEndTimestamp(LocalDateTime.now());
            appUserMoneyRepository.save(receiverMoney);
        }

        // Create new record for receiver with increased balance
        AppUserMoneyEntity newReceiverMoney = AppUserMoneyEntity.builder()
                .appUser(receiver)
                .currentMoney(receiverMoney.getCurrentMoney() + amount.intValue())
                .totalMoney(receiverMoney.getTotalMoney())  // totalMoney 유지
                .type("GIFT")
                .note(note != null ? note + " (from: " + sender.getNickname() + ")" : "머니 선물 받음 (from: " + sender.getNickname() + ")")
                .build();
        appUserMoneyRepository.save(newReceiverMoney);

        return newSenderMoney;  // 보내는 사람의 남은 잔액 정보 반환
    }

    @Transactional(readOnly = true)
    public Page<AppUserMoneyEntity> getUserMoneyHistory(AppUserEntity appUser, String type, Pageable pageable) {
        if (type != null) {
            return appUserMoneyRepository.findByAppUserAndTypeOrderByAppUserMoneyIdDesc(appUser, type, pageable);
        }
        return appUserMoneyRepository.findByAppUserOrderByAppUserMoneyIdDesc(appUser, pageable);
    }
} 