package com.cnco.campusflow.cscenter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
    name = "CsCenterReplyResponseDto",
    description = """
        고객센터 댓글 응답 DTO
        
        * 고객센터 댓글의 상세 정보를 포함합니다.
        * 댓글 내용, 작성자 정보, 등록일시 등의 기본 정보를 포함합니다.
        * 댓글의 계층 구조와 상태 정보를 포함합니다.
        * 도움이 되었나요(Y/N) 정보를 포함합니다.
        """,
    example = """
        {
            "replyId": 1,
            "content": "문의하신 내용 확인했습니다.",
            "upTreeId": null,
            "appUserId": 1,
            "level": 0,
            "nickname": "관리자",
            "insertTimestamp": "2024-03-19T10:00:00",
            "deleteYn": "N",
            "blindYn": "N"
        }
        """
)
public class CsCenterReplyResponseDto {
    @Schema(description = "댓글 내용", example = "문의하신 내용 확인했습니다.")
    private String content;

    @Schema(description = "상위 댓글 ID (대댓글인 경우)", example = "1")
    private Long upTreeId;

    @Schema(description = "작성자 ID", example = "1")
    private Long appUserId;

    @Schema(description = "댓글 레벨 (0: 댓글, 1: 대댓글)", example = "0")
    private Integer level;

    @Schema(description = "작성자 닉네임", example = "관리자")
    private String nickname;

    @Schema(description = "등록일시", example = "2024-03-19T10:00:00")
    private LocalDateTime insertTimestamp;

    @Schema(description = "댓글 ID", example = "1")
    private Long replyId;

    @Schema(
        description = """
            삭제 여부
            * Y: 삭제됨
            * N: 삭제되지 않음
            """,
        example = "N",
        allowableValues = {"Y", "N"}
    )
    private String deleteYn;

    @Schema(
        description = """
            블라인드 여부
            * Y: 블라인드 처리됨
            * N: 블라인드 처리되지 않음
            """,
        example = "N",
        allowableValues = {"Y", "N"}
    )
    private String blindYn;
} 