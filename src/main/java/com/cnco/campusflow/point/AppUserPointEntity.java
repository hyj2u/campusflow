package com.cnco.campusflow.point;

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
 * 사용자별 포인트 정보를 저장하는 엔티티 클래스
 * 
 * 주요 필드:
 * - appUserPointId: 사용자 포인트 ID (PK)
 * - appUser: 앱 사용자 정보 (ManyToOne)
 * - store: 매장 정보 (ManyToOne)
 * - totalPointCount: 누적 포인트 수
 * - currentPointCount: 현재 보유 포인트 수
 * - endTimestamp: 포인트 만료 일시
 * - note: 포인트 적립/사용 메모
 */
@Entity
@Table(name = "app_user_point", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AppUserPointEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "사용자 포인트 ID", example = "1")
    @Column
    private Long appUserPointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", nullable = false)
    @JsonIgnore
    @Schema(description = "앱 사용자 정보")
    private AppUserEntity appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    @JsonIgnore
    @Schema(description = "매장 정보")
    private StoreEntity store;

    @Schema(description = "누적 포인트 수", example = "1000")
    @Column(nullable = false)
    @Builder.Default
    private Integer totalPointCount = 0;

    @Schema(description = "현재 보유 포인트 수", example = "500")
    @Column(nullable = false)
    @Builder.Default
    private Integer currentPointCount = 0;

    @Schema(description = "포인트 만료 일시", example = "2024-12-31T23:59:59")
    @Column
    private LocalDateTime endTimestamp;

    @Schema(description = "포인트 적립/사용 메모", example = "커피 구매로 포인트 적립")
    @Column(length = 500)
    private String note;
} 