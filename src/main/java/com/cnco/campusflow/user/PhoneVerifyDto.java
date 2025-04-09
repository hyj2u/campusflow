package com.cnco.campusflow.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneVerifyDto {
    private String phone;
    private String inputCode;
    private String sentCode;
}
