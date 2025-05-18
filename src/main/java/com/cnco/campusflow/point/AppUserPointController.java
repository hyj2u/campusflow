package com.cnco.campusflow.point;

import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.common.ErrorRespDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
@Tag(
    name = "Point",
    description = "포인트 관련 API",
    externalDocs = @io.swagger.v3.oas.annotations.ExternalDocumentation(
        description = "포인트 정책 문서",
        url = "https://docs.example.com/point-policy"
    )
)
@SecurityRequirement(name = "bearerAuth")
public class AppUserPointController {

    private final AppUserPointService appUserPointService;

    @GetMapping("/{storeId}")
    @Operation(
        summary = "사용자 포인트 조회",
        description = """
            특정 매장에서의 사용자 포인트 정보를 조회합니다.
            
            - 현재 보유 포인트 수
            - 누적 포인트 수
            - 포인트 만료 일시
            - 포인트 관련 메모
            
            포인트 정보가 없는 경우 기본값(0)으로 초기화된 정보를 반환합니다.
            """,
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "조회 성공",
                content = @Content(schema = @Schema(implementation = AppUserPointEntity.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (예: 존재하지 않는 매장 ID)",
                content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "인증되지 않은 사용자",
                content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
            ),
            @ApiResponse(
                responseCode = "500",
                description = "서버 오류",
                content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
            )
        }
    )
    public ResponseEntity<AppUserPointEntity> getUserPoint(
        @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(
            description = "매장 ID",
            example = "1",
            required = true,
            schema = @Schema(type = "integer", format = "int64")
        ) @PathVariable Long storeId
    ) {
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(appUserPointService.getUserPoint(appUser, storeId));
    }

    @PostMapping("/{storeId}/use")
    @Operation(
        summary = "포인트 사용",
        description = """
            특정 매장에서 포인트를 사용합니다.
            
            - 사용할 포인트 수량은 현재 보유 포인트를 초과할 수 없습니다.
            - 포인트 사용 시 메모를 남길 수 있습니다.
            - 포인트 사용 후 현재 보유 포인트가 자동으로 차감됩니다.
            """,
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "사용 성공",
                content = @Content(schema = @Schema(implementation = AppUserPointEntity.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 또는 포인트 부족",
                content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "인증되지 않은 사용자",
                content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
            ),
            @ApiResponse(
                responseCode = "500",
                description = "서버 오류",
                content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
            )
        }
    )
    public ResponseEntity<AppUserPointEntity> usePoint(
        @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(
            description = "매장 ID",
            example = "1",
            required = true,
            schema = @Schema(type = "integer", format = "int64")
        ) @PathVariable Long storeId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "포인트 사용 요청",
            required = true,
            content = @Content(schema = @Schema(implementation = AppUserPointUseRequestDto.class))
        ) @RequestBody AppUserPointUseRequestDto requestDto
    ) {
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(appUserPointService.usePoint(appUser, storeId, requestDto.getUsePointCount(), requestDto.getNote()));
    }

    @PostMapping("/{storeId}/earn")
    @Operation(
        summary = "포인트 적립",
        description = """
            특정 매장에서 포인트를 적립합니다.
            
            - 적립할 포인트 수량은 1 이상이어야 합니다.
            - 포인트 적립 시 메모를 남길 수 있습니다.
            - 포인트 적립 후 현재 보유 포인트와 누적 포인트가 자동으로 증가합니다.
            """,
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "적립 성공",
                content = @Content(schema = @Schema(implementation = AppUserPointEntity.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청",
                content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "인증되지 않은 사용자",
                content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
            ),
            @ApiResponse(
                responseCode = "500",
                description = "서버 오류",
                content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
            )
        }
    )
    public ResponseEntity<AppUserPointEntity> earnPoint(
        @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(
            description = "매장 ID",
            example = "1",
            required = true,
            schema = @Schema(type = "integer", format = "int64")
        ) @PathVariable Long storeId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "포인트 적립 요청",
            required = true,
            content = @Content(schema = @Schema(implementation = AppUserPointEarnRequestDto.class))
        ) @RequestBody AppUserPointEarnRequestDto requestDto
    ) {
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(appUserPointService.earnPoint(appUser, storeId, requestDto.getEarnPointCount(), requestDto.getNote()));
    }
} 