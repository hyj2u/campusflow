package com.cnco.campusflow.coupon;

import com.cnco.campusflow.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 쿠폰 번호 생성을 위한 엔티티 클래스
 * 
 * 주요 필드:
 * - couponNumberId: 쿠폰 번호 ID (PK)
 * - couponName: 쿠폰명
 * - endDate: 쿠폰 만료일 (YYYY-MM-DD)
 * - issueCount: 발행 매수
 * - couponAmount: 쿠폰 금액
 * - status: 상태값 (W: 대기, C: 완료)
 */
@Entity
@Table(name = "coupon_gen", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper=false)
public class CouponGenEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long couponGenId;  // 쿠폰 번호 ID (PK)

    @Column(nullable = false, length = 100)
    private String couponName;    // 쿠폰명

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;    // 쿠폰 만료일 (YYYY-MM-DD)

    @Column(nullable = false)
    private Integer issueCount;   // 발행 매수

    @Column(nullable = false)
    private Integer couponAmount; // 쿠폰 금액

    @Column(nullable = false, length = 1)
    private String status;        // 상태값 (W: 대기, C: 승인)

    @Column(nullable = false)
    private String activeYn = "Y";
} 