package com.cnco.campusflow.notice;

import com.cnco.campusflow.image.ImageResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Schema(
    name = "NoticeBoardResponseDto",
    description = """
        공지사항 응답 DTO
        
        * 공지사항의 상세 정보를 포함합니다.
        * 제목, 내용, 등록일시, 조회수 등의 기본 정보를 포함합니다.
        * 작성자 정보와 이미지 목록을 포함합니다.
        * 푸시 알림, 카카오톡 알림, 전체 노출 여부 등의 설정 정보를 포함합니다.
        """,
    example = """
        {
            "boardId": 1,
            "title": "서비스 점검 안내",
            "content": "2024년 3월 20일 02:00 ~ 04:00 동안 서비스 점검이 진행됩니다.",
            "insertTimestamp": "2024-03-19T10:00:00",
            "viewCnt": 150,
            "pushYn": "Y",
            "talkYn": "Y",
            "fullExpYn": "Y",
            "brandId": 1,
            "boardType": "NOTICE",
            "nickname": "관리자",
            "appUserId": 1,
            "images": [
                {
                    "imageId": 1,
                    "imageUrl": "https://example.com/image1.jpg"
                }
            ],
            "profileImgUrl": "https://example.com/profile.jpg"
        }
        """
)
public class NoticeBoardResponseDto {
    @Schema(description = "게시글 ID", example = "1")
    private Long boardId;

    @Schema(description = "제목", example = "서비스 점검 안내")
    private String title;

    @Schema(description = "내용", example = "2024년 3월 20일 02:00 ~ 04:00 동안 서비스 점검이 진행됩니다.")
    private String content;

    @Schema(description = "등록일시", example = "2024-03-19T10:00:00")
    private LocalDateTime insertTimestamp;

    @Schema(description = "조회수", example = "150")
    private Integer viewCnt;

    @Schema(
        description = """
            푸시 알림 여부
            * Y: 푸시 알림 발송
            * N: 푸시 알림 미발송
            """,
        example = "Y",
        allowableValues = {"Y", "N"}
    )
    private String pushYn;

    @Schema(
        description = """
            카카오톡 알림 여부
            * Y: 카카오톡 알림 발송
            * N: 카카오톡 알림 미발송
            """,
        example = "Y",
        allowableValues = {"Y", "N"}
    )
    private String talkYn;

    @Schema(
        description = """
            전체 노출 여부
            * Y: 전체 노출
            * N: 선택 노출
            """,
        example = "Y",
        allowableValues = {"Y", "N"}
    )
    private String fullExpYn;

    @Schema(description = "브랜드 ID", example = "1")
    private Integer brandId;

    @Schema(
        description = """
            게시글 타입
            * EVENT: 이벤트
            * NOTICE: 공지사항
            """,
        example = "NOTICE",
        allowableValues = {"EVENT", "NOTICE"}
    )
    private String boardType;

    @Schema(description = "작성자 닉네임", example = "관리자")
    private String nickname;

    @Schema(description = "작성자 ID", example = "1")
    private Long appUserId;

    @Schema(description = "이미지 목록")
    private List<ImageResponseDto> images;

    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
    private String profileImgUrl;

    private List<Long> storeIds;
} 