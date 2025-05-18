package com.cnco.campusflow.stamp;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.store.StoreEntity;
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

/**
 * 사용자별 스탬프 정보를 저장하는 엔티티 클래스
 * 
 * 주요 필드:
 * - appUserStampId: 사용자 스탬프 ID (PK)
 * - appUser: 앱 사용자 정보 (ManyToOne)
 * - store: 매장 정보 (ManyToOne)
 * - totalStampCount: 누적 스탬프 수
 * - currentStampCount: 현재 보유 스탬프 수
 * - endTimestamp: 스탬프 만료 일시
 * - note: 스탬프 적립/사용 메모
 */
@Entity
@Table(name = "app_user_stamp", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AppUserStampEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "사용자 스탬프 ID", example = "1")
    @Column
    private Long appUserStampId;     // 사용자 스탬프 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", nullable = false)
    @JsonIgnore
    @Schema(description = "앱 사용자 정보")
    private AppUserEntity appUser;   // 앱 사용자 정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    @JsonIgnore
    @Schema(description = "매장 정보")
    private StoreEntity store;       // 매장 정보

    @Schema(description = "누적 스탬프 수", example = "10")
    @Column(nullable = false)
    @Builder.Default
    private Integer totalStampCount = 0;     // 누적 스탬프 수

    @Schema(description = "현재 보유 스탬프 수", example = "5")
    @Column(nullable = false)
    @Builder.Default
    private Integer currentStampCount = 0;   // 현재 보유 스탬프 수

    @Schema(description = "스탬프 만료 일시", example = "2024-12-31T23:59:59")
    @Column
    private LocalDateTime endTimestamp;      // 최신 row만 endTimestamp = NULL

    @Schema(description = "스탬프 적립/사용 메모", example = "커피 구매로 스탬프 적립")
    @Column(length = 500)
    private String note;
} 