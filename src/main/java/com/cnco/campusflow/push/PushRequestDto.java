package com.cnco.campusflow.push;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Push 발송 요청",
        example = """
                
                {
                  "recvUserId": 1,
                  "title": "알림 도착!",
                  "body": "새로운 강의가 등록되었습니다."
                }
                """
)
public class PushRequestDto {

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
} 