package com.cnco.campusflow.eventboard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(
    name = "EventBoardRequestDto",
    description = """
        이벤트 게시글 요청 DTO
        
        * 이벤트 게시글 생성/수정에 필요한 정보를 포함합니다.
        * 제목, 내용, 시작일시, 종료일시, 장소 등의 기본 정보를 포함합니다.
        * 시작일시는 종료일시보다 이전이어야 합니다.
        """,
    example = """
        {
            "eventId": 1,
            "title": "신규 매장 오픈 이벤트",
            "content": "강남 신규 매장 오픈을 기념하여 다양한 이벤트를 진행합니다.",
            "startDate": "2024-03-20T10:00:00",
            "endDate": "2024-04-20T18:00:00",
            "location": "강남점"
        }
        """
)
public class EventBoardRequestDto {
    @Schema(description = "이벤트 ID (수정 시에만 사용)", example = "1")
    private Long eventId;

    @Schema(description = "제목", example = "신규 매장 오픈 이벤트")
    private String title;

    @Schema(description = "내용", example = "강남 신규 매장 오픈을 기념하여 다양한 이벤트를 진행합니다.")
    private String content;

    @Schema(description = "시작일시", example = "2024-03-20T10:00:00")
    private LocalDateTime startDate;

    @Schema(description = "종료일시", example = "2024-04-20T18:00:00")
    private LocalDateTime endDate;

    @Schema(description = "장소", example = "강남점")
    private String location;
} 