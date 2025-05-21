package com.cnco.campusflow.cscenter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CsCenterBoardRequestDto {
    @Schema(description = "게시글 내용", example = "로그인 시도 시 오류가 발생합니다")
    private String content;

    @Schema(description = "문의 유형 코드", example = "ACCOUNT_LOGIN")
    private String codeCd;

    @Schema(description = "이미지 URL 목록")
    private List<String> images;
} 