package com.cnco.campusflow.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "RefreshRequestDto",
    description = """
        토큰 갱신 요청 DTO
        
        * 리프레시 토큰을 포함합니다.
        * 리프레시 토큰은 7일간 유효합니다.
        """,
    example = """
        {
            "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        }
        """
)
public class RefreshRequestDto {
    @NotBlank(message = "리프레시 토큰은 필수 입력값입니다.")
    @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", required = true)
    private String refreshToken;
}
