package com.cnco.campusflow.community;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "board_report", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "ReportEntity",
    description = """
        게시글 신고 엔티티
        
        * 게시글 신고 정보를 저장합니다.
        * 신고자와 신고된 게시글과 연관관계를 가집니다.
        * 신고 사유를 기록합니다.
        """,
    example = """
        {
            "reportId": 1,
            "board": {
                "boardId": 1,
                "title": "신고된 게시글"
            },
            "reason": "부적절한 내용",
            "appUser": {
                "appUserId": 2,
                "nickname": "신고자"
            }
        }
        """
)
public class ReportEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "신고 번호", example = "1")
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    @Schema(description = "신고된 게시글 정보")
    private CommunityBoardEntity board;

    @Column
    @Schema(description = "신고 사유", example = "부적절한 내용")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    @Schema(description = "신고자 정보")
    private AppUserEntity appUser;
}
