package com.cnco.campusflow.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserDto {
    private String userId;
    private String password;
    private String nickname;
    private String enterYear;
    private String major;
    private Long collegeId;
    private String phone;
    private String username;
    private Date birthday;

}
