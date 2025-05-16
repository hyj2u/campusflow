package com.cnco.campusflow.image;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Schema(
    name = "ImageResponseDto",
    description = """
        이미지 응답 DTO
        
        * 이미지 조회 시 반환되는 정보를 담습니다.
        * 이미지 ID와 접근 가능한 URL을 제공합니다.
        """,
    example = """
        {
            "imageId": 1,
            "imageUrl": "https://example.com/images/2024/03/example.jpg"
        }
        """
)
public class ImageResponseDto {
    @Schema(description = "이미지 번호", example = "1")
    private Long imageId;

    @Schema(description = "이미지 접근 URL", example = "https://example.com/images/2024/03/example.jpg")
    private String imageUrl;
}
