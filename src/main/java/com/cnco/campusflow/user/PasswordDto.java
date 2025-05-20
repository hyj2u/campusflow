package com.cnco.campusflow.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;


@Data
@Schema(
        name = "PasswordDto",
        description = """
        비밀번호 DTO  
        - 비밀번호 변경 시 사용됩니다.
        """,
        example = """
        {
          "password": "password123!"
        }
        """
)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasswordDto {


    @Schema(description = "비밀번호", example = "password123!")
    private String password;


}

