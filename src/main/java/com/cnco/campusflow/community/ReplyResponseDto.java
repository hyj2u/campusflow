package com.cnco.campusflow.community;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Schema(
    name = "ReplyResponseDto",
    description = """
        댓글 응답 DTO
        
        * 댓글 조회 시 반환되는 정보를 담습니다.
        * 작성자 정보, 단과대학 정보를 포함합니다.
        * 계층형 댓글 구조 정보도 제공됩니다.
        """,
    example = """
        {
            "replyId": 1,
            "content": "댓글 내용",
            "upTreeId": null,
            "appUserId": 1,
            "level": 0,
            "nickname": "작성자",
            "deleteYn" : "N",
            "blindYn" : "N",
            "insertTimestamp": "2024-03-20T10:00:00",
            "collegeName": "공과대학",
            "collegeAdmissionYear": "2024"
        }
        """
)
public class ReplyResponseDto {
    @Schema(description = "댓글 내용", example = "댓글 내용")
    private String content;

    @Schema(description = "상위 댓글 ID (답글인 경우 사용)", example = "1")
    private Long upTreeId;

    @Schema(description = "작성자 ID", example = "1")
    private Long appUserId;

    @Schema(description = "댓글 레벨 (0: 일반 댓글, 1: 답글)", example = "0")
    private Integer level;

    @Schema(description = "작성자 닉네임", example = "작성자")
    private String nickname;

    @Schema(description = "작성 시간", example = "2024-03-20T10:00:00")
    private LocalDateTime insertTimestamp;

    @Schema(description = "댓글 번호", example = "1")
    private Long replyId;
    @Schema(description = "삭제 여부", example = "N")
    private String deleteYn;
    @Schema(description = "블라인드처리 여부", example = "N")
    private String blindYn;

    @Schema(description = "단과대학 이름", example = "공과대학")
    private String collegeName;

    @Schema(description = "입학년도", example = "2024")
    private String collegeAdmissionYear;
}
