package com.cnco.campusflow.bannertype;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "배너 타입 응답 DTO")
public class BannerTypeResponseDto {
    @Schema(description = "배너 타입 ID", example = "1")
    private Long bannerTypeId;

    @Schema(description = "배너 타입 코드", example = "MAIN_BANNER")
    private String bannerTypeCd;

    @Schema(description = "배너 타입 이름", example = "메인 배너")
    private String bannerTypeNm;

    @Schema(description = "배너 타입 너비", example = "1920")
    private Integer bannerTypeWt;

    @Schema(description = "배너 타입 높이", example = "1080")
    private Integer bannerTypeHt;

    @Schema(description = "배너 타입 액션 코드", example = "LINK")
    private String bannerTypeAction;

    @Schema(description = "배너 타입 액션 이름", example = "링크")
    private String bannerTypeActionNm;

    @Schema(description = "활성화 여부", example = "Y")
    private Character activeYn;

    @Schema(description = "등록 일시", example = "2024-03-20T10:00:00")
    private LocalDateTime insertTimestamp;

    @Schema(description = "수정 일시", example = "2024-03-20T10:00:00")
    private LocalDateTime updateTimestamp;
}