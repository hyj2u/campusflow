package com.cnco.campusflow.coupon;

import com.cnco.campusflow.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 쿠폰 정보를 저장하는 엔티티 클래스
 * 
 * 주요 필드:
 * - couponId: 쿠폰 ID (PK)
 * - couponName: 쿠폰명
 * - couponAmount: 쿠폰 금액
 * - endDate: 쿠폰 만료일
 * - description: 쿠폰 설명
 * - sendType: 발송 유형 (P: 푸시, S: SMS)
 */
@Entity
@Table(name = "coupon", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper=false)
public class CouponEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long couponId;        // 쿠폰ID (PK)

    @Column(nullable = false, length = 100)
    private String couponName;    // 쿠폰명

    @Column(nullable = false)
    private Integer couponAmount; // 쿠폰금액

    @Column(nullable = false)
    private LocalDateTime endDate; // 쿠폰 만료일

    @Column(length = 500)
    private String description;   // 쿠폰 설명

    @Column(nullable = false, length = 1)
    private String sendType;      // 발송 유형 (P: 푸시, S: SMS)

    @Column(nullable = false)
    private String activeYn = "Y";
}