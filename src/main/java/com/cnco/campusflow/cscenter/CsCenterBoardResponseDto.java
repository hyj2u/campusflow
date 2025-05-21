package com.cnco.campusflow.cscenter;

import com.cnco.campusflow.image.ImageResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
    name = "CsCenterBoardResponseDto",
    description = """
        고객센터 게시글 응답 DTO
        
        * 고객센터 게시글의 상세 정보를 포함합니다.
        * 제목, 내용, 조회수, 게시글 타입 등의 기본 정보를 포함합니다.
        * 작성자 정보와 이미지 목록을 포함합니다.
        * 등록일시와 댓글 목록을 포함합니다.
        """,
    example = """
        {
            "boardId": 1,
            "title": "결제 오류 문의",
            "content": "결제 과정에서 오류가 발생했습니다.",
            "viewCnt": 150,
            "boardType": "QNA",
            "nickname": "홍길동",
            "appUserId": 1,
            "images": [
                {
                    "imageId": 1,
                    "imageUrl": "https://example.com/image1.jpg"
                }
            ],
            "profileImgUrl": "https://example.com/profile.jpg",
            "insertTimestamp": "2024-03-19T10:00:00",
            "replies": [
                {
                    "replyId": 1,
                    "content": "문의하신 내용 확인했습니다.",
                    "helpfulYn": "Y"
                }
            ]
        }
        """
)
public class CsCenterBoardResponseDto {
    @Schema(description = "게시글 ID", example = "1")
    private Long boardId;

    // @Schema(description = "제목", example = "결제 오류 문의")
    // private String title;

    @Schema(description = "내용", example = "결제 과정에서 오류가 발생했습니다.")
    private String content;

    @Schema(description = "조회수", example = "150")
    private Integer viewCnt;

    @Schema(
        description = """
            게시글 타입
            * QNA: 문의
            * SUGGESTION: 건의
            * COMPLAINT: 불만
            """,
        example = "QNA",
        allowableValues = {"QNA", "SUGGESTION", "COMPLAINT"}
    )
    private String boardType;

    @Schema(description = "작성자 닉네임", example = "홍길동")
    private String nickname;

    @Schema(description = "작성자 ID", example = "1")
    private Long appUserId;

    @Schema(description = "이미지 목록")
    private List<ImageResponseDto> images;

    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
    private String profileImgUrl;

    @Schema(description = "등록일시", example = "2024-03-19T10:00:00")
    private LocalDateTime insertTimestamp;

    @Schema(description = "댓글 목록")
    private List<CsCenterReplyResponseDto> replies;

    @Schema(description = "문의 유형 code_id", example = "10")
    private Long boardTypeCodeId;
} 