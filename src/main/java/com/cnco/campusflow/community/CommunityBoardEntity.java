package com.cnco.campusflow.community;

import com.cnco.campusflow.code.CodeEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.image.ImageEntity;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "community_board", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "CommunityBoardEntity",
    description = """
        커뮤니티 게시글 엔티티
        
        * 게시글의 기본 정보를 저장합니다.
        * 작성자, 게시글 유형, 이미지와 연관관계를 가집니다.
        * 비밀글 설정이 가능합니다.
        """,
    example = """
        {
            "boardId": 1,
            "title": "게시글 제목",
            "content": "게시글 내용",
            "viewCnt": 0,
            "secretYn": "N",
            "boardType": {
                "codeCd": "NOTICE",
                "codeNm": "공지사항"
            },
            "appUser": {
                "appUserId": 1,
                "nickname": "작성자"
            },
            "images": [
                {
                    "imageId": 1,
                    "imageUrl": "https://example.com/image.jpg"
                }
            ]
        }
        """
)
public class CommunityBoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "게시글 번호", example = "1")
    private Long boardId;   // 게시글 번호

    @Column
    @Schema(description = "게시글 제목", example = "게시글 제목")
    private String title;   // 제목
    @Column
    @Schema(description = "게시글 내용", example = "게시글 내용")
    private String content;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    @Schema(description = "작성자 정보")
    private AppUserEntity appUser;

    @Column
    @Schema(description = "조회수", example = "0")
    private Integer viewCnt;    // 조회(수)

    @Column
    @Schema(description = "비밀글 여부 (Y/N)", example = "N")
    private String secretYn;    // 비밀글여부
    @ManyToOne
    @JoinColumn(name="code_cd")
    @Schema(description = "게시글 유형")
    private CodeEntity boardType;
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "board_id")
    @Schema(description = "첨부된 이미지 목록")
    private List<ImageEntity> images; // 1:N 관계

}
