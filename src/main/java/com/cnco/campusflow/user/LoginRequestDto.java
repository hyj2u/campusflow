package com.cnco.campusflow.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequestDto {
    private String userId;
    private String password;
    private String deviceInfo;
    private String fcmToken;
}
