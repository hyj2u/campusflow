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

    @Schema(
        description = """
            현재 포인트
            * 현재 보유하고 있는 포인트
            * 기본값 0
            * 필수 입력 항목
            """,
        example = "500"
    )
    @Column(nullable = false)
    @Builder.Default
    private Integer currentPoint = 0;

    @Schema(
        description = """
            총 적립 포인트
            * 지금까지 적립한 총 포인트
            * 기본값 0
            * 필수 입력 항목
            """,
        example = "1000"
    )
    @Column(nullable = false)
    @Builder.Default
    private Integer totalPoint = 0;

    @Schema(description = "포인트 만료 일시", example = "2024-12-31T23:59:59")
    @Column
    private LocalDateTime endTimestamp;

    @Schema(description = "포인트 적립/사용 메모", example = "커피 구매로 포인트 적립")
    @Column(length = 500)
    private String note;

    @Schema(
        description = """
            거래 금액
            * 거래에 대한 금액
            * 필수 입력 항목
            * 양수: 적립
            * 음수: 사용
            """,
        example = "100"
    )
    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false, length = 10)
    @Schema(
        description = """
            거래 유형
            * USE: 사용
            * EARN: 적립
            * 필수 입력 항목
            """,
        example = "EARN",
        allowableValues = {"USE", "EARN"}
    )
    private String type;
} 