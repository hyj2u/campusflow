package com.cnco.campusflow.eventboard;

import com.cnco.campusflow.image.ImageResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Schema(
    name = "EventBoardResponseDto",
    description = """
        이벤트 게시글 응답 DTO
        
        * 이벤트 게시글의 상세 정보를 포함합니다.
        * 제목, 내용, 시작일시, 종료일시, 조회수 등의 기본 정보를 포함합니다.
        * 푸시 알림, 카카오톡 알림, 전체 노출 여부 등의 설정 정보를 포함합니다.
        * 작성자 정보와 이미지 목록을 포함합니다.
        * 관련 매장 ID 목록을 포함합니다.
        """,
    example = """
        {
            "boardId": 1,
            "title": "신규 매장 오픈 이벤트",
            "content": "강남 신규 매장 오픈을 기념하여 다양한 이벤트를 진행합니다.",
            "startDate": "2024-03-20",
            "endDate": "2024-04-20",
            "viewCnt": 150,
            "pushYn": "Y",
            "talkYn": "Y",
            "fullExpYn": "Y",
            "brandId": 1,
            "boardType": "EVENT",
            "nickname": "관리자",
            "appUserId": 1,
            "images": [
                {
                    "imageId": 1,
                    "imageUrl": "https://example.com/image1.jpg"
                }
            ],
            "profileImgUrl": "https://example.com/profile.jpg",
            "storeIds": [1, 2, 3]
        }
        """
)
public class EventBoardResponseDto {
    @Schema(description = "게시글 ID", example = "1")
    private Long boardId;

    @Schema(description = "제목", example = "신규 매장 오픈 이벤트")
    private String title;

    @Schema(description = "내용", example = "강남 신규 매장 오픈을 기념하여 다양한 이벤트를 진행합니다.")
    private String content;

    @Schema(description = "시작일", example = "2024-03-20")
    private LocalDate startDate;

    @Schema(description = "종료일", example = "2024-04-20")
    private LocalDate endDate;

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
        example = "EVENT",
        allowableValues = {"EVENT", "NOTICE"}
    )
    private String boardType;

    @Schema(description = "게시글 유형 코드 (code_cd)", example = "EVENT")
    private String boardTypeCodeCd;
    @Schema(description = "게시글 유형 PK (code_id)", example = "101")
    private Long boardTypeCodeId;

    @Schema(description = "작성자 닉네임", example = "관리자")
    private String nickname;

    @Schema(description = "작성자 ID", example = "1")
    private Long appUserId;

    @Schema(description = "이미지 목록")
    private List<ImageResponseDto> images;

    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
    private String profileImgUrl;

    @Schema(description = "이벤트 종료일이 지났는지 여부 (Y: 종료, N: 진행중)", example = "N", allowableValues = {"Y", "N"})
    private String endDateCheck;
} 