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
@Schema(description = "사용자 머니 엔티티")
public class AppUserMoneyEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "사용자 머니 ID", example = "1")
    @Column
    private Long appUserMoneyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", nullable = false)
    @JsonIgnore
    @Schema(description = "사용자 정보")
    private AppUserEntity appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = true)
    @JsonIgnore
    @Schema(description = "매장 정보")
    private StoreEntity store;

    @Schema(description = "현재 머니", example = "5000")
    @Column(nullable = false)
    @Builder.Default
    private Integer currentMoney = 0;

    @Schema(description = "총 적립 머니", example = "10000")
    @Column(nullable = false)
    @Builder.Default
    private Integer totalMoney = 0;

    @Column(nullable = false, length = 10)
    @Schema(description = "거래 유형 (CHARGE: 충전, USE: 사용, GIFT: 선물)", example = "CHARGE")
    private String type;

    @Schema(description = "거래 만료 일시", example = "2024-12-31T23:59:59")
    @Column
    private LocalDateTime endTimestamp;

    @Column(length = 500)
    @Schema(description = "거래 내역 메모", example = "신용카드 충전")
    private String note;
} 