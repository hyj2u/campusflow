package com.cnco.campusflow.community;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "ReportDto",
    description = """
        게시글 신고 DTO
        
        * 게시글 신고 시 필요한 정보를 담습니다.
        * 신고할 게시글 ID와 신고 사유는 필수 입력 항목입니다.
        """,
    example = """
        {
            "boardId": 1,
            "reason": "부적절한 내용"
        }
        """
)
public class ReportDto {
    @NotNull(message = "게시글 번호는 필수 입력 항목입니다")
    @Schema(description = "신고할 게시글 번호", example = "1", required = true)
    private Long boardId;

    @NotBlank(message = "신고 사유는 필수 입력 항목입니다")
    @Schema(description = "신고 사유", example = "부적절한 내용", required = true)
    private String reason;
}
