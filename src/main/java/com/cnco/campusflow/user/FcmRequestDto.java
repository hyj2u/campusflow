package com.cnco.campusflow.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "FcmRequestDto",
    description = """
        FCM Token 요청 DTO
        
        * fcmToken 정보
        """,
    example = """
        {
            "fcmToken": "fcmToken"
        }
        """
)
public class FcmRequestDto {

    @Schema(description = "FCM 토큰 (PUSH)", example = "fionienocs", required = true)
    private String fcmToken;
}
