package com.cnco.campusflow.common;

import com.cnco.campusflow.sendinfo.SendInfoEntity;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SendUtil {


    public String generateCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }
    public SendInfoEntity generateKakaoSendInfo(String msg, String title, String phone) {
        SendInfoEntity sendInfo = new SendInfoEntity();
        sendInfo.setSendMsg(msg);
        sendInfo.setSendStatus("PENDING");
        sendInfo.setSendTitle(title);
        sendInfo.setSendType("KAKAO");
        sendInfo.setPhone(phone);
        return sendInfo;
    }
}
