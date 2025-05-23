package com.cnco.campusflow.coupon;

import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.coupon.AppUserCouponRegisterRequestDto;
import com.cnco.campusflow.coupon.AppUserCouponResponseDto;
import com.cnco.campusflow.coupon.AppUserCouponUseRequestDto;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.common.ErrorRespDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
@Tag(
    name = "Coupon",
    description = """
        쿠폰 관련 API
        
        * 사용자의 쿠폰 목록 조회, 쿠폰 등록, 쿠폰 사용 기능을 제공합니다.
        * 모든 API는 JWT 토큰 인증이 필요합니다.
        * 쿠폰은 만료일이 지나지 않은 경우에만 사용 가능합니다.
        * 쿠폰 사용 시 useYn이 'Y'로 변경되고 재사용이 불가능합니다.
        """
)
public class AppUserCouponController {
    private final AppUserCouponService appUserCouponService;

    @GetMapping
    @Operation(
        summary = "쿠폰 목록 조회",
        description = """
            사용자의 쿠폰 목록을 조회합니다.
            
            - activeYn과 useYn 파라미터로 쿠폰 상태를 필터링할 수 있습니다.
            - activeYn: 활성화 여부 (Y/N, null 허용)
            - useYn: 사용 여부 (Y/N, null 허용)
            - 만료일 기준 오름차순 정렬됩니다.
            - 페이지네이션을 지원합니다 (page, size, sort 파라미터 사용 가능)
            """,
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "조회 성공",
                content = @Content(schema = @Schema(implementation = PaginatedResponse.class))
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
    public ResponseEntity<PaginatedResponse<AppUserCouponResponseDto>> getCouponList(
        @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(description = "활성화 여부 (Y/N, null 허용)") @RequestParam(required = false) String activeYn,
        @Parameter(description = "사용 여부 (Y/N, null 허용)") @RequestParam(required = false) String useYn,
        @PageableDefault(sort = "endDate", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(appUserCouponService.getCouponList(appUser, activeYn, useYn, pageable));
    }

    @PostMapping("/register")
    @Operation(
        summary = "쿠폰 등록",
        description = """
            쿠폰 번호를 입력하여 쿠폰을 등록합니다.
            
            - 쿠폰 번호는 'XXXX-XXXX-XXXX-XXXX' 형식입니다.
            - 등록 가능한 쿠폰은 activeYn='Y'이고 appUserId가 null인 경우입니다.
            - 만료일이 지나지 않은 쿠폰만 등록 가능합니다.
            - 이미 등록된 쿠폰은 재등록이 불가능합니다.
            """,
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "등록 성공",
                content = @Content(schema = @Schema(implementation = AppUserCouponResponseDto.class))
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
    public ResponseEntity<AppUserCouponResponseDto> registerCoupon(
        @AuthenticationPrincipal AppUserEntity appUser,
        @RequestBody AppUserCouponRegisterRequestDto requestDto
    ) {
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(appUserCouponService.registerCoupon(appUser, requestDto));
    }

    @PostMapping("/{appUserCouponId}/use")
    @Operation(
        summary = "쿠폰 사용",
        description = """
            쿠폰을 사용합니다.
            
            - 본인의 쿠폰만 사용할 수 있습니다.
            - 사용 가능한 쿠폰만 사용할 수 있습니다 (activeYn='Y', useYn='N').
            - 만료되지 않은 쿠폰만 사용할 수 있습니다.
            - 쿠폰 사용 시 머니 선물 처리됩니다.(보낸사람 : ADMIN, 받는사람 : 쿠폰 사용자)
            - 사용된 쿠폰은 재사용이 불가능합니다.
            """,
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "사용 성공",
                content = @Content(schema = @Schema(implementation = AppUserCouponResponseDto.class))
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
    public ResponseEntity<AppUserCouponResponseDto> useCoupon(
        @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(
            description = "사용자 쿠폰 ID",
            example = "1",
            required = true,
            schema = @Schema(type = "integer", format = "int64")
        ) @PathVariable Long appUserCouponId
    ) {
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(appUserCouponService.useCoupon(appUser, appUserCouponId));
    }
} 