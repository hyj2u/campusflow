package com.cnco.campusflow.userconf;

import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_userconf_noti", schema = "admin")
public class AppUserconfNotiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "알림 설정 ID", example = "1")
    private Long appUserconfNotiId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", nullable = false)
    @JsonIgnore
    private AppUserEntity appUser;

    @Schema(
        description = """
            강의 알림 수신 여부
            * Y: 수신
            * N: 미수신
            """,
        example = "N",
        allowableValues = {"Y", "N"}
    )
    @Column(nullable = false, length = 1)
    @Builder.Default
    private String lectureNotiYn = "N";

    @Schema(
        description = """
            주문현황 알림 수신 여부
            * Y: 수신
            * N: 미수신
            """,
        example = "N",
        allowableValues = {"Y", "N"}
    )
    @Column(nullable = false, length = 1)
    @Builder.Default
    private String orderNotiYn = "N";

    @Schema(
        description = """
            멤버십 알림 수신 여부
            * Y: 수신
            * N: 미수신
            """,
        example = "N",
        allowableValues = {"Y", "N"}
    )
    @Column(nullable = false, length = 1)
    @Builder.Default
    private String membershipNotiYn = "N";

    @Schema(
        description = """
            방해금지 설정 여부
            * Y: 설정
            * N: 미설정
            """,
        example = "N",
        allowableValues = {"Y", "N"}
    )
    @Column(nullable = false, length = 1)
    @Builder.Default
    private String doNotDisturbYn = "N";

    @Schema(description = "방해금지 시작 시간 (HH:MM)", example = "22:00")
    @Column
    private LocalTime doNotDisturbStartTime;

    @Schema(description = "방해금지 종료 시간 (HH:MM)", example = "08:00")
    @Column
    private LocalTime doNotDisturbEndTime;

    @Schema(
        description = """
            캠플소식 수신 여부
            * Y: 수신
            * N: 미수신
            """,
        example = "N",
        allowableValues = {"Y", "N"}
    )
    @Column(nullable = false, length = 1)
    @Builder.Default
    private String newsNotiYn = "N";

    @Schema(
        description = """
            마케팅 수신 동의 여부
            * Y: 동의
            * N: 미동의
            """,
        example = "N",
        allowableValues = {"Y", "N"}
    )
    @Column(nullable = false, length = 1)
    @Builder.Default
    private String marketingYn = "N";

    @Schema(description = "마케팅 수신 동의 시간", example = "2024-03-19T10:00:00")
    @Column
    private LocalDateTime marketingAgreeTimestamp;

    @Schema(
        description = """
            게시글 중단 알림 수신 여부
            * Y: 수신
            * N: 미수신
            """,
        example = "N",
        allowableValues = {"Y", "N"}
    )
    @Column(nullable = false, length = 1)
    @Builder.Default
    private String boardStopNotiYn = "N";

    @Schema(
        description = """
            답변 알림 수신 여부
            * Y: 수신
            * N: 미수신
            """,
        example = "N",
        allowableValues = {"Y", "N"}
    )
    @Column(nullable = false, length = 1)
    @Builder.Default
    private String answerNotiYn = "N";

    @Schema(
        description = """
            댓글 알림 수신 여부
            * Y: 수신
            * N: 미수신
            """,
        example = "N",
        allowableValues = {"Y", "N"}
    )
    @Column(nullable = false, length = 1)
    @Builder.Default
    private String replyNotiYn = "N";
} 