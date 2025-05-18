package com.cnco.campusflow.common;

import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FcmUtil {

    public void sendMessageToToken(String token, String title, String body) {
        Message message = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .setApnsConfig(buildApnsConfig(title, body))           // iOS 설정
                .setAndroidConfig(buildAndroidConfig(title, body))     // Android 설정
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("FCM 푸시 전송 성공: {}", response);
        } catch (FirebaseMessagingException e) {
            log.error("FCM 푸시 전송 실패", e);
            throw new RuntimeException("푸시 전송에 실패했습니다.");
        }
    }

    private AndroidConfig buildAndroidConfig(String title, String body) {
        return AndroidConfig.builder()
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .setSound("default")
                        .build())
                .build();
    }

    private ApnsConfig buildApnsConfig(String title, String body) {
        return ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setAlert(ApsAlert.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build())
                        .setSound("default")
                        .setBadge(1)
                        .build())
                .build();
    }
}
