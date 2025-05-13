package com.cnco.campusflow.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
            "userId": "test",
            "password": "test1234!!",
            "deviceInfo": "deviceInfo",
            "fcmToken": "fcmToken"
        }
        """
)
public class LoginRequestDto {

    @Schema(description = "로그인 아이디", example = "user123", required = true)
    private String userId;

    @Schema(description = "비밀번호", example = "password", required = true)
    private String password;
    @Schema(description = "디바이스정보", example = "IOS 15", required = true)
    private String deviceInfo;
    @Schema(description = "FCM 토큰 (PUSH)", example = "fionienocs", required = true)
    private String fcmToken;
}
