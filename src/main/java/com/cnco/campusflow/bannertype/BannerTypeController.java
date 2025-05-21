package com.cnco.campusflow.bannertype;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.common.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banner-type")
@RequiredArgsConstructor
@Tag(name = "Banner Type", description = "배너 타입 관리 API")
@SecurityRequirement(name = "bearerAuth")
public class BannerTypeController {

    private final BannerTypeService bannerTypeService;

    /**
     * 배너 타입 코드 목록을 조회합니다.
     * 활성화된 배너 타입의 ID와 이름으로 구성된 코드 목록을 반환합니다.
     */
    @GetMapping("/codes")
    @Operation(
        summary = "배너 타입 코드 목록 조회",
        description = "활성화된 배너 타입의 ID와 이름으로 구성된 코드 목록을 반환합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "배너 타입 코드 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = BannerTypeResponseDto.class))
        )
    })
    public ResponseEntity<CommonResponse<?>> getBannerTypeCodes() {
        List<BannerTypeResponseDto> codes = bannerTypeService.getBannerTypeCodes();
        return ResponseEntity.ok(CommonResponse.of(codes));
    }
}

