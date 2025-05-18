package com.cnco.campusflow.gifticon;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.product.ProductEntity;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.sendinfo.SendInfoEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 기프티콘 회원 정보를 저장하는 엔티티 클래스
 * 
 * 주요 필드:
 * - appUserGifticonId: 기프티콘 회원 ID (PK)
 * - phone: 연락처
 * - registerDate: 등록일시
 * - useYn: 사용 여부
 * - activeYn: 활성화 여부
 * - type: 구분 (GIFT: 선물, PURCHASE: 구매)
 * - receiver: 기프티콘을 받은 사용자
 * - sender: 기프티콘을 보낸 사용자 (선물인 경우)
 * - gifticon: 기프티콘 정보
 * - sendInfo: 발송 정보
 * - purchaseAmount: 구매금액
 */
@Entity
@Table(name = "app_user_gifticon", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper=false)
public class AppUserGifticonEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long appUserGifticonId; // 기프티콘 회원 ID (PK)
    
    @Column(length = 20)
    private String phone;         // 연락처

    @Column(nullable = false)
    private LocalDateTime registerDate; // 등록일시

    @Column(nullable = false, length = 1)
    private String useYn = "N"; // 사용 여부 (Y: 사용, N: 미사용)

    @Column(nullable = true, length = 10)
    private String type = "GIFT"; // 구분 (GIFT: 선물, PURCHASE: 구매)

    @Column(nullable = true)
    private LocalDateTime endDate; // 만료일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUserEntity receiver; // 기프티콘을 받은 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private AppUserEntity sender; // 기프티콘을 보낸 사용자 (선물인 경우)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gifticon_id")
    private GifticonEntity gifticon; // 기프티콘 정보 (관리자페이지에서 기프티콘을 생성한 경우)

    @Column(nullable = false, length = 1)
    private String activeYn = "Y";  // 활성화 여부 (Y: 활성화, N: 비활성화), 기본값 Y

    @OneToOne
    @JoinColumn(name = "send_info_id")
    private SendInfoEntity sendInfo; // 발송 정보

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product; // 상품 정보

    @Column(nullable = true)
    private Long purchaseAmount; // 구매금액
} 