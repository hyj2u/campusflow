package com.cnco.campusflow.community;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.user.AppUserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "community_like", schema = "admin")
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "CommunityLikeEntity",
    description = """
        게시글/댓글 좋아요 엔티티
        
        * 게시글또는 댓글 좋아요 정보를 저장합니다.
        * 좋아요한 당사자와 게시글과 연관관계를 가집니다.
        """,
    example = """
        {
            "likeId": 1,
            "board": {
                "boardId": 1,
                "title": "게시글 제목"
            },
            "appUser": {
                "appUserId": 1,
                "nickname": "작성자"
            }
       
        }
        """
)
public class CommunityLikeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "좋아요 번호", example = "1")
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "board_id" )
    @Schema(description = "좋아요 달린 게시글 정보")
    private CommunityBoardEntity board;
    @ManyToOne
    @JoinColumn(name = "reply_id" )
    @Schema(description = "좋아요 달린 댓글 정보")
    private ReplyEntity reply;


    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    @Schema(description = "좋아요한 작성자 정보")
    private AppUserEntity appUser;


}
