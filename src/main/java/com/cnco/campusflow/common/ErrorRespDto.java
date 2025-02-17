package com.cnco.campusflow.common;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorRespDto {
    private String msg;
    private String errorCode;

}
