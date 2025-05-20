package com.cnco.campusflow.coupon;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.sendinfo.SendInfoEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 쿠폰 회원 정보를 저장하는 엔티티 클래스
 * 
 * 주요 필드:
 * - appUserCouponId: 쿠폰 회원 ID (PK)
 * - phone: 연락처
 * - couponAmount: 쿠폰 금액
 * - couponNumber: 쿠폰 번호
 * - regiDate: 등록일시
 * - useYn: 사용 여부
 * - activeYn: 활성화 여부
 * - appUser: 앱 회원 정보
 * - coupon: 쿠폰 정보
 * - couponGen: 쿠폰 생성 정보
 * - sendInfo: 발송 정보
 */
@Entity
@Table(name = "app_user_coupon", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper=false)
@EntityListeners(AuditingEntityListener.class)
public class AppUserCouponEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long appUserCouponId;        // 쿠폰회원ID (PK)

    @Column
    private String phone;               // 연락처

    @Column(nullable = false)
    private Integer couponAmount;       // 쿠폰금액

    @Column(nullable = false, length = 20)
    private String couponNumber;        // 쿠폰번호

    @Column
    private LocalDateTime regiDate;

    @Column(nullable = false)
    private String useYn = "N";

    @Column(nullable = false)
    private String activeYn = "Y";

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUserEntity appUser;
    
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private CouponEntity coupon;
    
    @ManyToOne
    @JoinColumn(name = "coupon_gen_id")
    private CouponGenEntity couponGen;
}

