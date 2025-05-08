package com.cnco.campusflow.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "LoginRequestDto",
    description = """
        로그인 요청 DTO
        
        * 아이디와 비밀번호를 포함합니다.
        * 비밀번호는 암호화되어 전송되어야 합니다.
        """,
    example = """
        {
            "userId": "user123",
            "password": "encrypted_password"
        }
        """
)
public class LoginRequestDto {
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Schema(description = "로그인 아이디", example = "user123", required = true)
    private String userId;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Schema(description = "비밀번호 (암호화)", example = "encrypted_password", required = true)
    private String password;

    private String deviceInfo;
    private String fcmToken;
}
