package com.cnco.campusflow.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "PhoneVerifyDto",
    description = """
        전화번호 인증 DTO
        
        * 새로운 전화번호와 인증 코드를 포함합니다.
        * 인증 코드는 3분간 유효합니다.
        """,
    example = """
        {
            "phone": "01012345678",
            "code": "123456",
            "inputCode": "123456",
            "sentCode": "123456"
        }
        """
)
public class PhoneVerifyDto {
    @NotBlank(message = "전화번호는 필수 입력값입니다.")
    @Schema(description = "새로운 전화번호", example = "01012345678", required = true)
    private String phone;

    @NotBlank(message = "인증 코드는 필수 입력값입니다.")
    @Schema(description = "인증 코드", example = "123456", required = true)
    private String code;

    @Schema(description = "사용자가 입력한 인증 코드", example = "123456")
    private String inputCode;

    @Schema(description = "서버에서 발송한 인증 코드", example = "123456")
    private String sentCode;
}
