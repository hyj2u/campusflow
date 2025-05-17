package com.cnco.campusflow.community;

import com.cnco.campusflow.image.ImageResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "CommunityBoardResponseDto",
    description = """
        커뮤니티 게시글 응답 DTO
        
        * 게시글 조회 시 반환되는 정보를 담습니다.
        * 작성자 정보, 첨부 이미지, 생성/수정 시간을 포함합니다.
        * 단과대학 정보도 함께 제공됩니다.
        """,
    example = """
        {
            "boardId": 1,
            "title": "게시글 제목",
            "content": "게시글 내용",
            "secretYn": "N",
            "boardType": "NOTICE",
            "collegeName": "공과대학",
            "collegeId": 1,
            "viewCnt": 10,
            "appUserId": 1,
            "nickname": "작성자",
            "collegeAdmissionYear": "2024",
            "insertTimestamp": "2024-03-20T10:00:00",
            "images": [
                {
                    "imageId": 1,
                    "imageUrl": "https://example.com/image.jpg"
                }
            ]
        }
        """
)
public class CommunityBoardResponseDto {
    @Schema(description = "게시글 번호", example = "1")
    private Long boardId;

    @Schema(description = "게시글 제목", example = "게시글 제목")
    private String title;

    @Schema(description = "게시글 내용", example = "게시글 내용")
    private String content;

    @Schema(description = "비밀글 여부 (Y/N)", example = "N")
    private String secretYn;

    @Schema(description = "게시글 유형 코드", example = "NOTICE")
    private String boardType;

    @Schema(description = "단과대학 이름", example = "공과대학")
    private String collegeName;

    @Schema(description = "단과대학 ID", example = "1")
    private Integer collegeId;

    @Schema(description = "조회수", example = "10")
    private Integer viewCnt;

    @Schema(description = "좋아요수", example = "10")
    private Integer likeCnt;
    @Schema(description = "댓글 수 ", example = "10")
    private Integer replyCnt;
    @Schema(description = "작성자 프로필이미지 ", example = "10")
    private String profileImgUrl;

    @Schema(description = "작성자 ID", example = "1")
    private Long appUserId;

    @Schema(description = "작성자 닉네임", example = "작성자")
    private String nickname;
    @Schema(description = "대학 입학 년도 ",example = "2023")
    private String collegeAdmissionYear;

    @Schema(description = "작성 시간", example = "2024-03-20T10:00:00")
    private LocalDateTime insertTimestamp;

    @Schema(description = "첨부된 이미지 목록")
    private List<ImageResponseDto> images;


}
