package com.cnco.campusflow.money;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.store.StoreEntity;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user_money", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@Schema(
    name = "AppUserMoneyEntity",
    description = """
        사용자 머니 엔티티
        
        * 사용자의 머니 정보를 관리합니다.
        * 현재 보유 머니와 총 적립 머니를 추적합니다.
        * 머니 거래 내역을 기록합니다.
        * 거래 유형에 따라 머니가 증가하거나 감소합니다.
        """
)
public class AppUserMoneyEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
        description = """
            사용자 머니 ID
            * 자동 생성되는 고유 식별자
            """,
        example = "1"
    )
    @Column
    private Long appUserMoneyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", nullable = false)
    @JsonIgnore
    @Schema(
        description = """
            사용자 정보
            * 머니를 보유한 사용자
            * 필수 입력 항목
            """
    )
    private AppUserEntity appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = true)
    @JsonIgnore
    @Schema(
        description = """
            매장 정보
            * 머니 거래가 발생한 매장
            * 선택 입력 항목
            """
    )
    private StoreEntity store;

    @Schema(
        description = """
            현재 머니
            * 현재 보유하고 있는 머니
            * 기본값 0
            * 필수 입력 항목
            """,
        example = "5000"
    )
    @Column(nullable = false)
    @Builder.Default
    private Integer currentMoney = 0;

    @Schema(
        description = """
            총 적립 머니
            * 지금까지 적립한 총 머니
            * 기본값 0
            * 필수 입력 항목
            """,
        example = "10000"
    )
    @Column(nullable = false)
    @Builder.Default
    private Integer totalMoney = 0;

    @Column(nullable = false, length = 10)
    @Schema(
        description = """
            거래 유형
            * CHARGE: 충전
            * USE: 사용
            * GIFT: 선물
            * EARN: 적립
            * 필수 입력 항목
            """,
        example = "CHARGE",
        allowableValues = {"CHARGE", "USE", "GIFT", "EARN"}
    )
    private String type;

    @Schema(
        description = """
            거래 만료 일시
            * 해당 거래가 만료되는 시점
            * 선택 입력 항목
            """,
        example = "2024-12-31T23:59:59"
    )
    @Column
    private LocalDateTime endTimestamp;

    @Column(length = 500)
    @Schema(
        description = """
            거래 내역 메모
            * 거래에 대한 설명이나 관련 정보
            * 선택 입력 항목
            """,
        example = "신용카드 충전"
    )
    private String note;
} 