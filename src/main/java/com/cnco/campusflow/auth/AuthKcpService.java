package com.cnco.campusflow.auth;

import com.cnco.campusflow.brand.BrandEntity;
import com.cnco.campusflow.brand.BrandRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthKcpService {
    @Value("${kcp.site_cd}")
    private String siteCd;

    @Value("${kcp.web_siteid}")
    private String webSiteId;

    @Value("${kcp.ret_url}")
    private String retUrl;

    @Value("${kcp.cert_otp}")
    private String certOtp;

    @Value("${kcp.cert_enc_use}")
    private String certEncUse;
    public KcpAuthInitResponseDto generateKcpParams() {
        String ordrIdxx = UUID.randomUUID().toString().substring(0, 20); // KCP는 20자 제한

        // 암호화 모듈 처리: enc_data, integrity_value 생성
        String plainData = String.format(
                "cert_otp_use=%s&cert_enc_use=%s&web_siteid=%s&site_cd=%s&ordr_idxx=%s&return_url=%s",
                certOtp, certEncUse, webSiteId, siteCd, ordrIdxx, retUrl
        );

        String encData = "";         // ➜ kcp 암호화 함수 호출 필요
        String integrityValue = "";  // ➜ kcp 무결성 검증값 생성

        // kcp 암호화 라이브러리 호출 예:
        // encData = ct_cli.getEncData(plainData)
        // integrityValue = ct_cli.getHashData(plainData)

        return KcpAuthInitResponseDto.builder()
                .siteCd(siteCd)
                .ordrIdxx(ordrIdxx)
                .retUrl(retUrl)
                .certEncUse(certEncUse)
                .certOtpUse(certOtp)
                .encData(encData)
                .integrityValue(integrityValue)
                .build();
    }
}
