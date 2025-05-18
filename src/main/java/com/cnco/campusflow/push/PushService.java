package com.cnco.campusflow.push;

import com.cnco.campusflow.common.FcmUtil;
import com.cnco.campusflow.jwt.RefreshTokenEntity;
import com.cnco.campusflow.jwt.RefreshTokenRepository;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PushService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final AppUserRepository appUserRepository;
    private final FcmUtil fcmService;

    public void sendPushToUser(PushRequestDto dto) {
        AppUserEntity user = appUserRepository.findById(dto.getRecvUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        List<RefreshTokenEntity> fcmTokens = refreshTokenRepository.findByAppUser(user);
        for(RefreshTokenEntity fcmToken : fcmTokens) {
            String token = fcmToken.getToken();
            if (fcmToken == null ) {
                throw new IllegalStateException("해당 사용자에 등록된 FCM 토큰이 없습니다.");
            }
            fcmService.sendMessageToToken(token, dto.getTitle(), dto.getBody());
        }

    }
}
