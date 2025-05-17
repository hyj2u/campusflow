package com.cnco.campusflow.gifticon;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.product.ProductEntity;
import com.cnco.campusflow.store.StoreEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.cnco.campusflow.user.AppUserEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 기프티콘 정보를 저장하는 엔티티 클래스
 * 
 * 주요 필드:
 * - gifticonId: 기프티콘 ID (PK)
 * - gifticonName: 기프티콘명
 * - endDate: 만료일
 * - sendType: 발송 유형 (P: 푸시, S: SMS)
 * - type: 구분 (GIFT: 선물, PURCHASE: 구매)
 * - storeId: 매장 ID (FK)
 * - productId: 상품 ID (FK)
 * - description: 기프티콘 설명
 */
@Entity
@Table(name = "gifticon", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper=false)
@EntityListeners(AuditingEntityListener.class)
public class GifticonEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long gifticonId;      // 기프티콘 ID (PK)

    @Column(nullable = false, length = 100)
    private String gifticonName;  // 기프티콘명

    @Column(nullable = false)
    private LocalDateTime endDate; // 만료일

    @Column(nullable = false, length = 1)
    private String sendType;      // 발송 유형 (P: 푸시, S: SMS)

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product; // 상품 정보

    @Column(length = 500)
    private String description;   // 기프티콘 설명

    @Column(nullable = false, length = 1)
    private String activeYn = "Y";  // 활성화 여부 (Y: 활성화, N: 비활성화), 기본값 Y

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUserEntity appUser;  // 사용자 정보

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
} 