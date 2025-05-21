package com.cnco.campusflow.banner;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(description = "배너 상세 응답 DTO")
public class BannerDtlResponseDto {
    @Schema(description = "배너 ID", example = "1")
    private Long bannerId;

    @Schema(description = "배너 이름", example = "메인 배너")
    private String bannerNm;

    @Schema(description = "배너 이미지", example = "banner.jpg")
    private String bannerImg;

    @Schema(description = "배너 URL", example = "https://example.com")
    private String bannerUrl;

    @Schema(description = "활성화 여부", example = "Y")
    private Character activeYn;

    @Schema(description = "배너 시작일", example = "2024-03-20")
    private LocalDate bannerStart;

    @Schema(description = "배너 종료일", example = "2024-03-27")
    private LocalDate bannerEnd;

    @Schema(description = "배너 타입 ID", example = "1")
    private Long bannerTypeId;

    @Schema(description = "배너 타입 이름", example = "메인 배너")
    private String bannerTypeNm;

    @Schema(description = "이미지 ID", example = "1")
    private Long imageId;

    @Schema(description = "이미지 경로", example = "banners/main.jpg")
    private String imgPath;

    @Schema(description = "이미지 전체 경로", example = "https://example.com/images/banners/main.jpg")
    private String imgFullPath;

    @Schema(description = "전체 노출 여부", example = "Y")
    private Character fullExpYn;
}