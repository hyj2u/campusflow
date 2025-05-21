package com.cnco.campusflow.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import lombok.Data;

import java.util.Date;


@Data
@Schema(
        name = "AppUserDto",
        description = """
        사용자 정보 DTO  
        - 회원가입 또는 사용자 정보 수정 시 사용됩니다.
        - 수정시에는 nickname, collegeAdmissionYear, major, collegeId 필수
        """,
        example = """
        {
          "userId": "user123",
          "password": "password123!",
          "nickname": "홍길동",
          "collegeAdmissionYear": "2022",
          "major": "컴퓨터공학과",
          "collegeId": 1,
          "phone": "01012345678",
          "username": "길동이",
          "birthday": "2000-01-01"
        }
        """
)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserDto {

    @Schema(description = "사용자 ID (로그인용)", example = "user123")
    private String userId;

    @Schema(description = "비밀번호", example = "password123!")
    private String password;

    @Schema(description = "닉네임", example = "홍길동")
    private String nickname;

    @Schema(description = "입학년도 (YYYY)", example = "2022")
    private String collegeAdmissionYear;

    @Schema(description = "전공", example = "컴퓨터공학과")
    private String major;

    @Schema(description = "대학 ID", example = "1")
    private Long collegeId;

    @Schema(description = "휴대폰 번호", example = "01012345678")
    private String phone;

    @Schema(description = "실명", example = "길동이")
    private String username;

    @Schema(description = "생년월일", example = "2000-01-01")
    private Date birthday;
}

