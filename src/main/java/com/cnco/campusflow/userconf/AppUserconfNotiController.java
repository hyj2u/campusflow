package com.cnco.campusflow.userconf;

import com.cnco.campusflow.user.AppUserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "사용자 알림 설정",
    description = """
        사용자 알림 설정 API
        
        * 사용자의 알림 설정을 조회하고 수정할 수 있습니다.
        * 각 알림 유형별로 수신 여부를 설정할 수 있습니다.
        * 방해금지 모드 설정이 가능합니다.
        * 마케팅 수신 동의 여부를 설정할 수 있습니다.
        """
)
@RestController
@RequestMapping("/app-noti")
@RequiredArgsConstructor
public class AppUserconfNotiController {

    private final AppUserconfNotiService appUserconfNotiService;

    @Operation(
        summary = "사용자 알림 설정 조회",
        description = "현재 로그인한 사용자의 알림 설정 정보를 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "알림 설정 조회 성공",
            content = @Content(schema = @Schema(implementation = AppUserconfNotiEntity.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "인증되지 않은 사용자"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류"
        )
    })
    @GetMapping
    public ResponseEntity<AppUserconfNotiEntity> getUserNotiConfig(
        @AuthenticationPrincipal AppUserEntity appUser
    ) {
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }
        
        try {
            AppUserconfNotiEntity notiConfig = appUserconfNotiService.getUserNotiConfig(appUser);
            return ResponseEntity.ok(notiConfig);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
        summary = "사용자 알림 설정 생성/수정",
        description = """
            사용자의 알림 설정을 생성하거나 수정합니다.
            
            * 일부 필드만 전달할 경우 해당 필드만 업데이트됩니다.
            * 전달하지 않은 필드는 기존 값을 유지합니다.
            * Y/N 값은 대소문자를 구분합니다.
            * 시간 형식은 HH:mm 형식을 따라야 합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "알림 설정 생성/수정 성공",
            content = @Content(schema = @Schema(implementation = AppUserconfNotiEntity.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 (잘못된 값 형식 등)"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "인증되지 않은 사용자"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "서버 내부 오류"
        )
    })
    @PostMapping
    public ResponseEntity<AppUserconfNotiEntity> createOrUpdateNotiConfig(
        @AuthenticationPrincipal AppUserEntity appUser,
        
        @Parameter(description = "알림 설정 정보", required = true)
        @RequestBody AppUserconfNotiEntity requestEntity
    ) {
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }
        
        try {
            AppUserconfNotiEntity notiConfig = appUserconfNotiService.createOrUpdateNotiConfig(appUser, requestEntity);
            return ResponseEntity.ok(notiConfig);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
} 