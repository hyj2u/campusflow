package com.cnco.campusflow.news;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(
    name = "NewsRequestDto",
    description = """
        뉴스 등록 요청 DTO
        
        * 뉴스 등록 시 필요한 정보를 담습니다.
        * URL은 필수 입력 항목입니다.
        """,
    example = """
        {
            "url": "https://example.com/news/1",
            "activeYn": "Y"
        }
        """
)
public class NewsRequestDto {
    @NotBlank(message = "URL은 필수 입력 항목입니다")
    @Schema(description = "뉴스 URL", example = "https://example.com/news/1", required = true)
    private String url;

    @Schema(description = "활성화 여부", example = "Y", allowableValues = {"Y", "N"})
    private String activeYn = "Y";  // 기본값 Y
} 