package com.cnco.campusflow.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenReponseDto {
    private String accessToken;
    private String refreshToken;

}
