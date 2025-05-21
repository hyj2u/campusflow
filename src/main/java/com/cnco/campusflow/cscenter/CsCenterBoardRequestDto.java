package com.cnco.campusflow.cscenter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(
    name = "CsCenterBoardRequestDto",
    description = """
        고객센터 게시글 요청 DTO
        
        * 게시글 작성 시 필요한 정보를 담습니다.
        * 내용과 문의 유형은 필수 입력 항목입니다.
        """,
    example = "{\"content\":\"로그인이 안됩니다.\",\"codeCd\":\"ACCOUNT_LOGIN\"}"
)
public class CsCenterBoardRequestDto {
    @NotBlank(message = "내용은 필수 입력 항목입니다")
    @Schema(description = "게시글 내용", example = "로그인이 안됩니다.", required = true)
    private String content;

    @NotNull(message = "문의 유형은 필수 입력 항목입니다")
    @Schema(
        description = """
            문의 유형 코드
            
            가능한 값:
            * ACCOUNT_LOGIN - 로그인 관련
            * ACCOUNT_AUTH - 인증 관련
            * ACCOUNT_EXIT - 탈퇴 관련
            * ACCOUNT_OTHER - 기타 계정 관련
            """,
        example = "ACCOUNT_LOGIN",
        required = true,
        allowableValues = {"ACCOUNT_LOGIN", "ACCOUNT_AUTH", "ACCOUNT_EXIT", "ACCOUNT_OTHER"}
    )
    private String codeCd;

    @Schema(description = "이미지 URL 목록")
    private List<String> images;
} 