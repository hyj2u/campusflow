package com.cnco.campusflow.community;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "ReportDto",
    description = """
        게시글/댓글 신고 DTO
        
        * 게시글 신고 시 필요한 정보를 담습니다.
        * 신고할 게시글 ID 또는 댓글 ID 와 신고 사유는 필수 입력 항목입니다.
        * 게시글ID 가 있는 경우는 게시글 신고, 댓글 ID가 있는 경우는 댓글 신고 
        """,
    example = """
        {
            "boardId": 1,
            "reason": "부적절한 내용",
            "replyId": 2
        }
        """
)
public class ReportDto {

    @Schema(description = "신고할 게시글 번호", example = "1")
    private Long boardId;
    @Schema(description = "신고할 댓글 번호", example = "1")
    private Long replyId;

    @Schema(description = "신고 사유", example = "부적절한 내용", required = true)
    private String reason;
}
