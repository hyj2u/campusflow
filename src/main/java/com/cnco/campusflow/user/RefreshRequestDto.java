package com.cnco.campusflow.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefreshRequestDto {
    private String refreshToken;
}
