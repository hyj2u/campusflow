package com.cnco.campusflow.push;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Push", description = "앱 Push관련 API")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/push")
@RequiredArgsConstructor
public class PushController {

    private final PushService pushService;



    @Operation(
        summary = "Push 발송",
        description = """
            사용자에게 Push를 발송합니다. 
            """,
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Push 발송 성공",
                content = @Content(
                    schema = @Schema(
                        implementation = PushResponseDto.class,
                        description = "Push 발송 결과"
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 또는 스탬프 부족"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
        }
    )
    @PostMapping("/send")
    public ResponseEntity<?> sendPush(@RequestBody PushRequestDto requestDto) {
        return ResponseEntity.ok( pushService.sendPushToUser(requestDto));
    }


} 