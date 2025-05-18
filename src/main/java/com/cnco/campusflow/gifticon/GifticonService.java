package com.cnco.campusflow.gifticon;

import com.cnco.campusflow.product.ProductEntity;
import com.cnco.campusflow.product.ProductRepository;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.user.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GifticonService {
    private final AppUserGifticonRepository appUserGifticonRepository;
    private final AppUserRepository appUserRepository;
    private final ProductRepository productRepository;

    /**
     * 기프티콘 목록을 조회합니다.
     */
    public Page<AppUserGifticonResponseDto> getGifticonList(AppUserEntity appUser, String activeYn, String type, Pageable pageable) {
        try {
            log.info("기프티콘 목록 조회 시작 - userId: {}, type: {}, activeYn: {}", 
                    appUser.getUserId(), type, activeYn);
            
            Page<AppUserGifticonResponseDto> gifticons = appUserGifticonRepository.findAppUserGifticonList(appUser, activeYn, type, pageable);

            log.info("기프티콘 목록 조회 완료 - 총 {}건", gifticons.getTotalElements());
            return new PageImpl<>(gifticons.getContent(), pageable, gifticons.getTotalElements());
        } catch (Exception e) {
            log.error("기프티콘 목록 조회 중 오류 발생 - 원인: {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 새로운 기프티콘을 생성합니다.
     */
    public void createAppUserGifticons(AppUserGifticonRequestDto requestDto, AppUserEntity currentUser) {
        try {
            log.info("기프티콘 생성 시작 - userId: {}, productId: {}, quantity: {}", 
                    currentUser.getUserId(), requestDto.getProductId(), requestDto.getQuantity());

            // 1. 상품 존재 여부 확인
            ProductEntity product = productRepository.findById(requestDto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

            // 2. 전화번호 리스트 검증
            List<String> phoneNumbers = requestDto.getPhoneNumbers();
            if (phoneNumbers != null && phoneNumbers.size() > requestDto.getQuantity()) {
                throw new IllegalArgumentException("전화번호 개수는 구매 수량보다 클 수 없습니다.");
            }

            // 3. 개당 구매금액 계산 (원단위 절상)
            long totalAmount = requestDto.getPurchaseAmount();
            int quantity = requestDto.getQuantity();
            long amountPerGifticon = (long) Math.ceil((double) totalAmount / quantity / 100) * 100;

            // 4. 기프티콘 생성
            List<AppUserGifticonEntity> gifticons = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();

            for (int i = 0; i < quantity; i++) {
                AppUserGifticonEntity gifticon = new AppUserGifticonEntity();
                gifticon.setProduct(product);
                gifticon.setRegisterDate(now);
                gifticon.setUseYn("N");
                gifticon.setActiveYn("Y");
                gifticon.setPurchaseAmount(amountPerGifticon);

                // 전화번호가 있고, 현재 인덱스가 전화번호 리스트 범위 내에 있는 경우
                if (phoneNumbers != null && i < phoneNumbers.size()) {
                    String phone = phoneNumbers.get(i);
                    gifticon.setPhone(phone);
                    gifticon.setType("GIFT");
                    gifticon.setSender(currentUser);

                    // 전화번호로 사용자 찾기
                    appUserRepository.findByPhone(phone).ifPresent(gifticon::setReceiver);
                } else {
                    // 전화번호가 없거나 리스트 범위를 벗어난 경우
                    gifticon.setType("PURCHASE");
                    gifticon.setReceiver(currentUser);
                }

                gifticons.add(gifticon);
            }

            // 5. 일괄 저장
            appUserGifticonRepository.saveAll(gifticons);

            log.info("기프티콘 생성 완료 - userId: {}, 생성된 기프티콘 수: {}, 개당 금액: {}", 
                    currentUser.getUserId(), gifticons.size(), amountPerGifticon);
        } catch (Exception e) {
            log.error("기프티콘 생성 중 오류 발생 - 원인: {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage(), e);
            throw e;
        }
    }
} 