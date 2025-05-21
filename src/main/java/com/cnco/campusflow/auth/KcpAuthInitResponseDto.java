package com.cnco.campusflow.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KcpAuthInitResponseDto {
    private String siteCd;
    private String ordrIdxx;
    private String retUrl;
    private String certMthd = "01"; // 고정
    private String certEncUse;
    private String certOtpUse;
    private String encData;
    private String integrityValue;
}
