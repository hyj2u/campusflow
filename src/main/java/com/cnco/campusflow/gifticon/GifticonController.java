package com.cnco.campusflow.gifticon;

import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.common.ErrorRespDto;
import com.cnco.campusflow.user.AppUserEntity;
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
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "기프티콘",
    description = """
        기프티콘 API
        
        * 사용자의 기프티콘 목록을 조회하고 새로운 기프티콘을 생성할 수 있습니다.
        * 기프티콘은 구매(PURCHASE)와 받은 기프티콘(GIFT) 두 가지 타입이 있습니다.
        * 구매 시에는 현재 로그인한 사용자가 수신자가 됩니다.
        * 선물 시에는 전화번호로 수신자를 지정할 수 있습니다.
        * 구매 금액은 수량으로 나누어 100원 단위로 올림하여 각 기프티콘에 할당됩니다.
        """
)
@RestController
@RequestMapping("/gifticon")
@RequiredArgsConstructor
public class GifticonController {
    private final GifticonService gifticonService;

    @Operation(
        summary = "기프티콘 목록 조회",
        description = """
            로그인한 사용자의 기프티콘 목록을 조회합니다.
            
            * 페이지 번호는 0부터 시작합니다.
            * activeYn, type은 null을 허용하며 모든 기프티콘을 조회할 수 있습니다.
            * activeYn으로 사용 가능한 기프티콘만 필터링할 수 있습니다.
            * type으로 구매(PURCHASE) 또는 받은 기프티콘(GIFT) 기프티콘만 필터링할 수 있습니다.
            * 정렬 기준을 지정할 수 있으며, 기본값은 appUserGifticonId 기준 내림차순입니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "기프티콘 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = PaginatedResponse.class))
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
    public PaginatedResponse<AppUserGifticonResponseDto> getGifticonList(
            @AuthenticationPrincipal AppUserEntity appUser,
            @Parameter(description = "기프티콘 사용 여부 (Y/N)", example = "N")
            @RequestParam(required = false) String useYn,
            @Parameter(description = "매장 ID", example = "1")
            @RequestParam(required = false) Long storeId,
            @Parameter(description = "기프티콘 타입 (PURCHASE/GIFT)", example = "PURCHASE")
            @RequestParam(required = false) String type,
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "appUserGifticonId", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<AppUserGifticonResponseDto> gifticons = gifticonService.getGifticonList(appUser, useYn, storeId, type, pageable);

        return new PaginatedResponse<>(
                gifticons.getContent(),
                gifticons.getNumber(),
                gifticons.getSize(),
                gifticons.getTotalElements(),
                gifticons.getTotalPages()
        );
    }

    @Operation(
        summary = "기프티콘 생성",
        description = """
            새로운 기프티콘을 생성합니다.
            
            * 상품 ID와 구매 수량, 총 구매 금액을 필수로 입력해야 합니다.
            * 선물할 경우 phoneNumbers 배열에 수신자 전화번호를 포함해야 합니다. (본인 구매의 경우 전화번호 불필요)
            * phoneNumbers의 개수는 구매 수량보다 클 수 없습니다.
            * 구매 금액은 수량으로 나누어 100원 단위로 올림하여 각 기프티콘에 할당됩니다.
            * 선물 시에는 전화번호로 수신자를 찾아 연결합니다.
            * 수신자를 찾지 못한 경우에도 기프티콘은 생성되며, 전화번호는 저장됩니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "기프티콘 생성 성공"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 (상품이 존재하지 않음, 전화번호 개수 초과 등)"
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
    public ResponseEntity<Void> createGifticons(
            @Valid @RequestBody AppUserGifticonRequestDto requestDto,
            @AuthenticationPrincipal AppUserEntity currentUser) {
        gifticonService.createAppUserGifticons(requestDto, currentUser);
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "기프티콘 사용",
        description = """
            기프티콘을 사용합니다.
            
            * 본인의 기프티콘만 사용할 수 있습니다.
            * 사용 가능한 기프티콘만 사용할 수 있습니다 (activeYn='Y', useYn='N').
            * 만료되지 않은 기프티콘만 사용할 수 있습니다.
            * 사용된 기프티콘은 재사용이 불가능합니다.
            * 매장 ID는 기프티콘의 상품이 속한 매장과 일치해야 합니다.
            """,
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "사용 성공",
                content = @Content(schema = @Schema(implementation = AppUserGifticonResponseDto.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (기프티콘을 찾을 수 없음, 본인의 기프티콘이 아님, 사용할 수 없는 기프티콘, 만료된 기프티콘, 매장 불일치)",
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
    @PostMapping("/{appUserGifticonId}/use")
    public ResponseEntity<AppUserGifticonResponseDto> useGifticon(
        @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(
            description = "사용자 기프티콘 ID",
            example = "1",
            required = true,
            schema = @Schema(type = "integer", format = "int64")
        ) @PathVariable Long appUserGifticonId
    ) {
        if (appUser == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(gifticonService.useGifticon(appUser, appUserGifticonId));
    }
} 