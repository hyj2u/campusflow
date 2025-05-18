package com.cnco.campusflow.stamp;

import com.cnco.campusflow.user.AppUserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Stamp", description = "스탬프 관련 API")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/stamp")
@RequiredArgsConstructor
public class AppUserStampController {

    private final AppUserStampService appUserStampService;

    @Operation(
        summary = "사용자 스탬프 조회",
        description = """
            특정 매장의 사용자 스탬프 정보를 조회합니다.
            
            - 현재 보유 중인 스탬프 정보를 반환합니다.
            - 스탬프 정보가 없는 경우 기본값(0)으로 초기화된 정보를 반환합니다.
            - endTimestamp가 null인 row가 현재 활성화된 스탬프 정보입니다.
            """,
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "조회 성공",
                content = @Content(
                    schema = @Schema(
                        implementation = AppUserStampEntity.class,
                        description = """
                            스탬프 정보 응답
                            - appUserStampId: 스탬프 ID
                            - totalStampCount: 누적 스탬프 수
                            - currentStampCount: 현재 보유 스탬프 수
                            - endTimestamp: 스탬프 만료 일시 (null인 경우 현재 활성화된 스탬프)
                            - note: 스탬프 관련 메모
                            """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
        }
    )
    @GetMapping("/{storeId}")
    public ResponseEntity<AppUserStampEntity> getUserStamp(
        @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(
            description = "매장 ID",
            example = "1",
            required = true,
            schema = @Schema(type = "integer", format = "int64")
        )
        @PathVariable Long storeId
    ) {
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(appUserStampService.getUserStamp(appUser, storeId));
    }

    @Operation(
        summary = "스탬프 사용",
        description = """
            특정 매장의 스탬프를 사용합니다.
            
            - 현재 보유 중인 스탬프 수보다 많은 수를 사용할 수 없습니다.
            - 사용한 스탬프 수만큼 currentStampCount가 감소합니다.
            - totalStampCount는 유지됩니다.
            """,
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "스탬프 사용 성공",
                content = @Content(
                    schema = @Schema(
                        implementation = AppUserStampEntity.class,
                        description = "스탬프 사용 후 업데이트된 정보"
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 또는 스탬프 부족"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
        }
    )
    @PostMapping("/{storeId}/use")
    public ResponseEntity<AppUserStampEntity> useStamp(
        @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(
            description = "매장 ID",
            example = "1",
            required = true,
            schema = @Schema(type = "integer", format = "int64")
        )
        @PathVariable Long storeId,
        @Valid @RequestBody AppUserStampUseRequestDto request
    ) {
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(appUserStampService.useStamp(appUser, storeId, request.getUseStampCount(), request.getNote()));
    }

    @Operation(
        summary = "스탬프 적립",
        description = """
            특정 매장의 스탬프를 적립합니다.
            
            - 적립할 스탬프 수는 1 이상이어야 합니다.
            - 적립한 스탬프 수만큼 currentStampCount와 totalStampCount가 증가합니다.
            - note 필드를 통해 적립 사유를 기록할 수 있습니다.
            """,
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "스탬프 적립 성공",
                content = @Content(
                    schema = @Schema(
                        implementation = AppUserStampEntity.class,
                        description = "스탬프 적립 후 업데이트된 정보"
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
        }
    )
    @PostMapping("/{storeId}/earn")
    public ResponseEntity<AppUserStampEntity> earnStamp(
        @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(
            description = "매장 ID",
            example = "1",
            required = true,
            schema = @Schema(type = "integer", format = "int64")
        )
        @PathVariable Long storeId,
        @Valid @RequestBody AppUserStampEarnRequestDto request
    ) {
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(appUserStampService.earnStamp(appUser, storeId, request.getEarnStampCount(), request.getNote()));
    }
} 