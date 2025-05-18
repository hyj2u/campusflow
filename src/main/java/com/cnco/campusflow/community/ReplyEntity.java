package com.cnco.campusflow.community;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.user.AppUserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "reply", schema = "admin")
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "ReplyEntity",
    description = """
        댓글 엔티티
        
        * 게시글의 댓글 정보를 저장합니다.
        * 작성자와 게시글과 연관관계를 가집니다.
        * 계층형 댓글 구조를 지원합니다.
        """,
    example = """
        {
            "replyId": 1,
            "board": {
                "boardId": 1,
                "title": "게시글 제목"
            },
            "content": "댓글 내용",
            "appUser": {
                "appUserId": 1,
                "nickname": "작성자"
            },
            "level": 0,
            "upTreeId": null
        }
        """
)
public class ReplyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "댓글 번호", example = "1")
    private Long replyId;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    @Schema(description = "댓글이 달린 게시글 정보")
    private CommunityBoardEntity board;

    @Column
    @Schema(description = "댓글 내용", example = "댓글 내용")
    private String content;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    @Schema(description = "댓글 작성자 정보")
    private AppUserEntity appUser;

    @Column
    @Schema(description = "댓글 레벨 (0: 일반 댓글, 1: 답글)", example = "0")
    private Integer level;

    @Column
    @Schema(description = "상위 댓글 ID (답글인 경우 사용)", example = "1")
    private Long upTreeId;
    @Column
    @Schema(description = "좋아요 수", example = "1")
    private Integer likeCnt;
    @Column
    @Schema(description = "삭제 여부", example = "N")
    private String deleteYn;
    @Column
    @Schema(description = "블라인드처리 여부", example = "N")
    private String blindYn;
    @Column
    @Schema(description = "비밀글 여부", example = "N")
    private String secretYn;
}
