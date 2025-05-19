package com.cnco.campusflow.push;

import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.user.AppUserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                    @ApiResponse(responseCode = "400", description = "잘못된 요청" ),
                    @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    @PostMapping("/send")
    public ResponseEntity<?> sendPush(@RequestBody PushRequestDto requestDto) {
        return ResponseEntity.ok(pushService.sendPushToUser(requestDto));
    }

    @Operation(
            summary = "Push 발송된 리스트 조회",
            description = """
                사용자에게 발송된 푸시 알림 리스트를 페이징하여 조회합니다.
                `sendType`이 null이면 전체 푸시를, 값이 있으면 해당 타입에 한해 필터링합니다.
                """,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공",
                            content = @Content(
                                    schema = @Schema(implementation = PaginatedResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    @GetMapping("/list")
    public ResponseEntity<PaginatedResponse<PushResponseDto>> getPushList(
            @AuthenticationPrincipal AppUserEntity appUser,
            @Parameter(
                    description = "푸시 발송 타입 (예: COMM, ORDER, NOTICE)",
                    example = "COMM"
            )
            @RequestParam(required = false) String sendType,

            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "sendDttm", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        PaginatedResponse<PushResponseDto> response = pushService.getPushList(appUser, sendType, pageable);
        return ResponseEntity.ok(response);
    }
    @Operation(
            summary = "Push 알람 삭제",
            description = """
                사용자에게 발송된 푸시 알림 리스트에서 제거
                """,
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "삭제 성공",
                            content = @Content(
                                    schema = @Schema(implementation = PaginatedResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    @DeleteMapping("/{appUserPushId}")
    public ResponseEntity<?> deletePush(
            @Parameter(
                    description = "푸시 ID",
                    example = "63"
            )
            @PathVariable Long appUserPushId
    ) {
        pushService.deletePush(appUserPushId);
        return ResponseEntity.noContent().build();
    }



} 