package com.cnco.campusflow.banner;

import com.cnco.campusflow.category.CategoryHqResponseDto;
import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.common.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import com.cnco.admin.common.PaginatedResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cnco.campusflow.image.ImageEntity;

import java.math.BigInteger;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/banner")
@RequiredArgsConstructor
@Tag(name = "Banner", description = "배너 관리 API")
@SecurityRequirement(name = "bearerAuth")
public class BannerController {

    private final BannerService bannerService;

    @GetMapping
    @Operation(
        summary = "배너 목록 조회",
        description = "배너 목록을 페이지네이션하여 조회합니다. 매장 ID와 배너 타입 코드로 필터링할 수 있습니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "배너 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = BannerResponseDto.class))
        )
    })
    public PaginatedResponse<BannerResponseDto> getBannerTypes(
            @Parameter(description = "매장 ID", required = false)
            @RequestParam(required = false) Long storeId,
            @Parameter(description = "배너 타입 코드(banner-type/codes 조회)", required = false)
            @RequestParam(required = false) String bannerTypeCd,
            @Parameter(hidden = true) @PageableDefault(size = 10) Pageable pageable) {

        // 서비스 호출하여 데이터 조회
        Page<BannerResponseDto> banners = bannerService.getBanners(storeId, bannerTypeCd, pageable);

        PaginatedResponse<BannerResponseDto> response = new PaginatedResponse<>(
                banners.getContent(),
                banners.getNumber(),
                banners.getSize(),
                banners.getTotalElements(),
                banners.getTotalPages()
        );
        return response;
    }

    /* 배너 상세 조회 */
    @GetMapping("/{id}")
    @Operation(
        summary = "배너 상세 조회",
        description = "특정 배너의 상세 정보를 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "배너 상세 조회 성공",
            content = @Content(schema = @Schema(implementation = BannerDtlResponseDto.class))
        ),
        @ApiResponse(responseCode = "404", description = "배너를 찾을 수 없음")
    })
    public ResponseEntity<CommonResponse<?>> getBannerDetail(
        @Parameter(description = "배너 ID", required = true, example = "1")
        @PathVariable Long id
    ) {
        BannerDtlResponseDto detail = bannerService.getBannerDetail(id);
        return ResponseEntity.ok(CommonResponse.of(detail));
    }
}

