package com.cnco.campusflow.push;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Push 발송 요청",
        example = """
                {
                    "recvUserId": 1,
                    "title": "알림 도착!",
                    "body": "새로운 강의가 등록되었습니다.",
                    "type": null,
                    "sendType": "COMM",
                    "sendSubType": "댓글",
                    "appUserPushId": 55,
                    "sendStatus": "SUCCESS"
                }
                """
)
public class PushResponseDto {

    @Schema(
            description = "수신자 app user id",
            example = "1"
    )
    private Long recvUserId;
    @Schema(
            description = "제목",
            example = "[Campusflow]"
    )
    private String title;
    @Schema(
            description = "제목",
            example = "내용입니다."
    )
    private String body;

    @Schema(
            description = "알림 분류",
            example = "COMM",
            allowableValues = {"COMM", "ORDER", "NOTICE"}
    )
    private String sendType;
    @Schema(
            description = "알림 상세 분류",
            example = "댓글"
    )
    private String sendSubType;
    @Schema(
            description = "알림 Key",
            example = "1"
    )
    private Long appUserPushId;
    @Schema(
            description = "PUSH 상태",
            example = "SUCCESS"
    )
    private String sendStatus;
} 