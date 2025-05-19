package com.cnco.campusflow.money;

import com.cnco.campusflow.common.ErrorRespDto;
import com.cnco.campusflow.store.StoreEntity;
import com.cnco.campusflow.store.StoreRepository;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.user.AppUserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.cnco.campusflow.common.PaginatedResponse;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/money")
@RequiredArgsConstructor
@Tag(
    name = "머니 관리",
    description = """
        사용자 머니 관리 API
        
        * 사용자의 머니 적립, 사용, 선물 기능을 제공합니다.
        * 머니 사용 이력을 조회할 수 있습니다.
        * 모든 API는 인증된 사용자만 접근 가능합니다.
        * 머니는 1원 이상의 정수값만 사용 가능합니다.
        * 머니 사용 시 잔액이 부족하면 400 에러가 발생합니다.
        """
)
public class AppUserMoneyController {

    private final AppUserMoneyService appUserMoneyService;
    private final StoreRepository storeRepository;
    private final AppUserRepository appUserRepository;

    @GetMapping
    @Operation(
        summary = "사용자 머니 조회",
        description = """
            사용자의 현재 활성화된 머니 내역을 조회합니다.
            
            * 현재 보유한 머니와 총 적립 머니를 반환합니다.
            * 활성화된 머니 내역이 없는 경우 404 에러를 반환합니다.
            * 인증되지 않은 사용자는 401 에러를 반환합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(schema = @Schema(implementation = AppUserMoneyEntity.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "인증되지 않은 사용자",
            content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "활성화된 머니 내역이 없음",
            content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
        )
    })
    public ResponseEntity<AppUserMoneyEntity> getUserMoney(
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity appUser) {
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }

        try {
            AppUserMoneyEntity money = appUserMoneyService.getUserMoney(appUser);
            return ResponseEntity.ok(money);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("No active money record found")) {
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }

    @PostMapping("/earn")
    @Operation(
        summary = "머니 적립",
        description = """
            사용자의 머니를 적립합니다.
            
            * 적립할 머니는 1원 이상이어야 합니다.
            * 머니 적립 시 메모를 남길 수 있습니다.
            * 머니 적립 후 현재 보유 머니와 총 적립 머니가 자동으로 증가합니다.
            * 인증되지 않은 사용자는 401 에러를 반환합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "적립 성공",
            content = @Content(schema = @Schema(implementation = AppUserMoneyEntity.class))
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
        )
    })
    public ResponseEntity<AppUserMoneyEntity> earnMoney(
            @Parameter(description = "머니 적립 요청")
            @Valid @RequestBody AppUserMoneyEarnRequestDto requestDto,
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity appUser) {
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }

        AppUserMoneyEntity moneyEntity = appUserMoneyService.earnMoney(
                appUser, null, requestDto.getAmount(), requestDto.getNote());
        return ResponseEntity.ok(moneyEntity);
    }

    @PostMapping("/use")
    @Operation(
        summary = "머니 사용",
        description = """
            사용자의 머니를 사용합니다.
            
            * 사용할 머니는 1원 이상이어야 합니다.
            * 현재 보유한 머니보다 많이 사용할 수 없습니다.
            * 머니 사용 시 메모를 남길 수 있습니다.
            * 머니 사용 후 현재 보유 머니가 감소합니다.
            * 인증되지 않은 사용자는 401 에러를 반환합니다.
            * 매장을 찾을 수 없는 경우 404 에러를 반환합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "사용 성공",
            content = @Content(schema = @Schema(implementation = AppUserMoneyEntity.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 또는 잔액 부족",
            content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "인증되지 않은 사용자",
            content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "매장을 찾을 수 없음",
            content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
        )
    })
    public ResponseEntity<AppUserMoneyEntity> useMoney(
            @Parameter(description = "머니 사용 요청")
            @Valid @RequestBody AppUserMoneyUseRequestDto requestDto,
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity appUser) {
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }

        StoreEntity store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        try {
            AppUserMoneyEntity moneyEntity = appUserMoneyService.useMoney(
                    appUser, store, requestDto.getAmount(), requestDto.getNote());
            return ResponseEntity.ok(moneyEntity);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Insufficient balance")) {
                return ResponseEntity.badRequest().build();
            }
            throw e;
        }
    }

    @PostMapping("/gift")
    @Operation(
        summary = "머니 선물",
        description = """
            다른 사용자에게 머니를 선물합니다.
            
            * 선물할 머니는 1원 이상이어야 합니다.
            * 현재 보유한 머니보다 많이 선물할 수 없습니다.
            * 자기 자신에게는 선물할 수 없습니다.
            * 선물 시 메모를 남길 수 있습니다.
            * 선물 후 현재 보유 머니가 감소합니다.
            * 인증되지 않은 사용자는 401 에러를 반환합니다.
            * 받는 사람을 찾을 수 없는 경우 404 에러를 반환합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "선물 성공",
            content = @Content(schema = @Schema(implementation = AppUserMoneyEntity.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 또는 잔액 부족",
            content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "인증되지 않은 사용자",
            content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "받는 사람을 찾을 수 없음",
            content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
        )
    })
    public ResponseEntity<AppUserMoneyEntity> giftMoney(
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity appUser,
            @Parameter(description = "머니 선물 요청")
            @RequestBody AppUserMoneyGiftRequestDto requestDto) {
        if (appUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        AppUserEntity receiver = appUserRepository.findByAppUserId(requestDto.getAppUserId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        AppUserMoneyEntity result = appUserMoneyService.giftMoney(
                appUser,
                receiver,
                requestDto.getAmount(),
                requestDto.getNote()
        );

        return ResponseEntity.ok(result);
    }

    @GetMapping("/history")
    @Operation(
        summary = "머니 사용 이력 조회",
        description = """
            사용자의 머니 사용 이력을 조회합니다.
            
            * 페이지 번호는 0부터 시작합니다.
            * ID 기준 내림차순으로 정렬됩니다.
            * type 파라미터로 특정 타입의 이력만 조회할 수 있습니다.
            * 정렬 기준을 지정할 수 있으며, 기본값은 appUserMoneyId 기준 내림차순입니다.
            * 인증되지 않은 사용자는 401 에러를 반환합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "이력 조회 성공",
            content = @Content(schema = @Schema(implementation = PaginatedResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "인증되지 않은 사용자",
            content = @Content(schema = @Schema(implementation = ErrorRespDto.class))
        )
    })
    public ResponseEntity<PaginatedResponse<AppUserMoneyEntity>> getUserMoneyHistory(
            @Parameter(description = "인증된 사용자 정보", hidden = true)
            @AuthenticationPrincipal AppUserEntity appUser,
            @Parameter(
                description = """
                    머니 타입
                    * EARN: 적립
                    * USE: 사용
                    * CHARGE: 충전
                    * GIFT: 선물
                    """,
                example = "EARN"
            )
            @RequestParam(required = false) String type,
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "appUserMoneyId", direction = Sort.Direction.DESC) Pageable pageable) {
        
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }

        Page<AppUserMoneyEntity> moneyHistory = appUserMoneyService.getUserMoneyHistory(appUser, type, pageable);

        return ResponseEntity.ok(new PaginatedResponse<>(
                moneyHistory.getContent(),
                moneyHistory.getNumber(),
                moneyHistory.getSize(),
                moneyHistory.getTotalElements(),
                moneyHistory.getTotalPages()
        ));
    }
} 