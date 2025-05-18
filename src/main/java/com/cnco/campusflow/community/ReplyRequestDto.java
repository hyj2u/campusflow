package com.cnco.campusflow.community;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "ReplyRequestDto",
    description = """
        댓글 요청 DTO
        
        * 댓글 작성/수정 시 필요한 정보를 담습니다.
        * 댓글 내용은 필수 입력 항목입니다.
        * 답글 작성 시 상위 댓글 ID가 필요합니다.
        """,
    example = """
        {
            "replyId": 1,
            "content": "댓글 내용",
            "upTreeId": 1.
            "secretYn": "Y"
        }
        """
)
public class ReplyRequestDto {
    @Schema(description = "댓글 번호 (수정 시에만 사용)", example = "1")
    private Long replyId;

    @NotBlank(message = "댓글 내용은 필수 입력 항목입니다")
    @Schema(description = "댓글 내용", example = "댓글 내용", required = true)
    private String content;

    @Schema(description = "상위 댓글 ID (답글인 경우 사용)", example = "1")
    private Long upTreeId;

    @Schema(description = "비밀글 여부", example = "N")
    private String secretYn;
}
