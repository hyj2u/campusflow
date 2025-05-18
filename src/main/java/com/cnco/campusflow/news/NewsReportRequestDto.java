package com.cnco.campusflow.news;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(
    name = "NewsReportRequestDto",
    description = """
        뉴스 신고 요청 DTO
        
        * 신고유형, 코멘트만 입력받습니다. (newsId는 URL Path에서 받음)
        """,
    example = """
        {
            "reportReason": "(카테고리 선택) 잘못된 정보를 제공하고 있어요.",
            "comment": "(사용자 직접입력 부분) 본문 내용을 보면 사실과 다른 내용이에요. 수정하여 주세요"
        }
        """
)
public class NewsReportRequestDto {
    @NotBlank(message = "신고유형(사유)은 필수 입력 항목입니다")
    @Schema(description = "신고유형(사유)", example = "부적절한 내용", required = true)
    private String reportReason;

    @Schema(description = "신고 코멘트", example = "욕설이 포함되어 있습니다.")
    private String comment;
} 