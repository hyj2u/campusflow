package com.cnco.campusflow.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "UserRequestDto",
    description = """
        회원 삭제 요청 DTO
        """,
    example = """
        {
            "deleteReason": "삭제 이유"
        }
        """
)
public class UserDeleteRequestDto {

    @Schema(description = "삭제 이유", example = "이사", required = true)
    private String deleteReason;
}
