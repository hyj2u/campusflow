package com.cnco.campusflow.point;

import com.cnco.campusflow.store.StoreEntity;
import com.cnco.campusflow.store.StoreRepository;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.common.ErrorRespDto;
import com.cnco.campusflow.common.PaginatedResponse;
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

import java.util.List;

@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
@Tag(
    name = "Point",
    description = "포인트 관련 API"
)
@SecurityRequirement(name = "bearerAuth")
public class AppUserPointController {

    private final AppUserPointService appUserPointService;
    private final StoreRepository storeRepository;

    @GetMapping("/{storeId}")
    @Operation(
        summary = "사용자 포인트 조회",
        description = """
            특정 매장에서의 사용자 포인트 정보를 조회합니다.
            
            - 현재 보유 포인트 수
            - 누적 포인트 수
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
            사용자의 포인트를 사용합니다.
            
            - 사용할 포인트는 1 이상이어야 합니다.
            - 현재 보유 포인트보다 많은 포인트를 사용할 수 없습니다.
            - 포인트 사용 시 메모를 남길 수 있습니다.
            """,
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "포인트 사용 성공",
                content = @Content(schema = @Schema(implementation = AppUserPointEntity.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = """
                    잘못된 요청
                    - 포인트가 부족한 경우
                    - 잘못된 매장 ID
                    - 포인트 금액이 1 미만인 경우
                    """,
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
            @RequestBody AppUserPointRequestDto requestDto) {
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }
        StoreEntity store = storeRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다. (매장 ID: " + storeId + ")"));
        return ResponseEntity.ok(appUserPointService.usePoint(appUser, store, requestDto.getAmount(), requestDto.getNote()));
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
                description = """
                    잘못된 요청
                    - 잘못된 매장 ID
                    - 포인트 금액이 1 미만인 경우
                    """,
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
        @RequestBody AppUserPointRequestDto requestDto
    ) {
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }
        StoreEntity store = storeRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다. (매장 ID: " + storeId + ")"));
        return ResponseEntity.ok(appUserPointService.earnPoint(appUser, store, requestDto.getAmount(), requestDto.getNote()));
    }

    @GetMapping("/{storeId}/history")
    @Operation(
        summary = "포인트 이력 조회",
        description = """
            특정 매장에서의 사용자 포인트 이력을 조회합니다.
            
            - 포인트 적립/사용 내역
            - 포인트 금액
            - 포인트 메모
            - 포인트 처리 시간
            
            필터링:
            - type: 포인트 유형 (EARN: 적립, USE: 사용)
            - type이 없으면 전체 내역 조회
            
            페이징 처리:
            - page: 페이지 번호 (0부터 시작)
            - size: 페이지당 항목 수
            """,
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "조회 성공",
                content = @Content(schema = @Schema(implementation = PaginatedResponse.class))
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
    public ResponseEntity<PaginatedResponse<AppUserPointEntity>> getPointHistory(
        @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(
            description = "매장 ID",
            example = "1",
            required = true,
            schema = @Schema(type = "integer", format = "int64")
        ) @PathVariable Long storeId,
        @Parameter(
            description = "포인트 유형 (EARN: 적립, USE: 사용)",
            example = "EARN",
            schema = @Schema(type = "string", allowableValues = {"EARN", "USE"})
        ) @RequestParam(required = false) String type,
        @Parameter(
            description = "페이지 번호 (0부터 시작)",
            example = "0",
            schema = @Schema(type = "integer", defaultValue = "0")
        ) @RequestParam(defaultValue = "0") int page,
        @Parameter(
            description = "페이지당 항목 수",
            example = "10",
            schema = @Schema(type = "integer", defaultValue = "10")
        ) @RequestParam(defaultValue = "10") int size
    ) {
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }
        StoreEntity store = storeRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다. (매장 ID: " + storeId + ")"));
        return ResponseEntity.ok(appUserPointService.getPointHistory(appUser, store, type, page, size));
    }
}