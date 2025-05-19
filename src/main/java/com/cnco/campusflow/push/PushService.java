package com.cnco.campusflow.push;

import com.cnco.campusflow.code.CodeRepository;
import com.cnco.campusflow.common.FcmUtil;
import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.jwt.RefreshTokenEntity;
import com.cnco.campusflow.jwt.RefreshTokenRepository;
import com.cnco.campusflow.sendinfo.SendInfoEntity;
import com.cnco.campusflow.sendinfo.SendInfoRepository;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PushService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final AppUserRepository appUserRepository;
    private final SendInfoRepository sendInfoRepository;
    private final CodeRepository codeRepository;
    private final AppUserPushRepository appUserPushRepository;
    private final FcmUtil fcmService;

    public PushResponseDto sendPushToUser(PushRequestDto dto) {
        AppUserEntity user = appUserRepository.findById(dto.getRecvUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        List<RefreshTokenEntity> fcmTokens = refreshTokenRepository.findByAppUser(user);
        if (fcmTokens.isEmpty()) {
            throw new IllegalStateException("해당 사용자에 등록된 FCM 토큰이 없습니다.");
        }
     /*   for (RefreshTokenEntity fcmToken : fcmTokens) {
            String token = fcmToken.getToken();
            if (token != null && !token.isBlank()) {
                fcmService.sendMessageToToken(token, dto.getTitle(), dto.getBody());
            }
        }*/

        SendInfoEntity sendInfo = new SendInfoEntity();
        sendInfo.setPhone(user.getPhone());
        sendInfo.setReqSendTm(LocalDateTime.now());
        sendInfo.setSentTm(LocalDateTime.now());
        sendInfo.setSendMsg(dto.getBody());
        sendInfo.setSendStatus("SUCCESS");
        sendInfo.setSendTitle(dto.getTitle());
        sendInfo.setSendType("P");
        sendInfo.setReceiver(user);
        sendInfo = sendInfoRepository.save(sendInfo);
        AppUserPushEntity pushEntity = new AppUserPushEntity();
        pushEntity.setAppUser(user);
        pushEntity.setSendInfo(sendInfo);
        pushEntity.setActiveYn("Y");
        pushEntity.setSendDttm(sendInfo.getSentTm());
        pushEntity.setSendStatus("SUCCESS");
        pushEntity.setSendType(codeRepository.findByCodeCd(dto.getSendType()).get());
        pushEntity.setSendSubType(dto.getSendSubType());
        pushEntity.setShowYn("Y");
        pushEntity=appUserPushRepository.save(pushEntity);
        return convertPushToDto(pushEntity);

    }
    public  PaginatedResponse<PushResponseDto>getPushList(AppUserEntity appUser, String sendType, Pageable pageable) {
        Page<AppUserPushEntity> pushEntities = appUserPushRepository.getPushList(appUser, sendType, pageable);
        List<PushResponseDto> dtoList = pushEntities.getContent().stream()
                .map(this::convertPushToDto)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                dtoList,
                pushEntities.getNumber(),
                pushEntities.getSize(),
                pushEntities.getTotalElements(),
                pushEntities.getTotalPages()
        );
    }
    public  void deletePush(Long appUserPushId) {
      AppUserPushEntity appUserPushEntity= appUserPushRepository.findById(appUserPushId).get();
      appUserPushEntity.setShowYn("N");
      appUserPushRepository.save(appUserPushEntity);
    }
    private PushResponseDto convertPushToDto(AppUserPushEntity entity) {
        PushResponseDto dto = new PushResponseDto();
        dto.setRecvUserId(entity.getAppUser().getAppUserId());
        dto.setTitle(entity.getSendInfo().getSendTitle());
        dto.setBody(entity.getSendInfo().getSendMsg());
        dto.setSendStatus(entity.getSendStatus());
        dto.setSendType(entity.getSendType().getCodeCd());
        dto.setSendSubType(entity.getSendSubType());
        dto.setAppUserPushId(entity.getAppUserPushId());
        return dto;
    }

}
