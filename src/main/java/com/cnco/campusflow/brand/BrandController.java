package com.cnco.campusflow.brand;

import com.cnco.campusflow.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
@Transactional
@Tag(
    name = "Brand",
    description = """
        브랜드 관리 API
        
        * 브랜드 목록 조회
        * 브랜드 추가/수정
        * 브랜드 삭제
        
        모든 API는 JWT 인증이 필요합니다.
        """
)
@SecurityRequirement(name = "bearerAuth")
public class BrandController {
    private final BrandService brandService;

    @Operation(
        summary = "브랜드 목록 조회",
        description = """
            정렬 순서(orderNum) 기준으로 정렬된 브랜드 목록을 조회합니다.
            
            * 정렬 순서가 낮은 순서대로 정렬됩니다.
            * 삭제되지 않은 브랜드만 조회됩니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "브랜드 목록 조회 성공"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 접근"),
        @ApiResponse(responseCode = "403", description = "접근 권한 없음")
    })
    @GetMapping
    public CommonResponse<?> getBrands() {
        return CommonResponse.of(brandService.getBrands());
    }

    @Operation(
        summary = "브랜드 추가/수정",
        description = """
            새로운 브랜드를 추가하거나 기존 브랜드를 수정합니다.
            
            * brandId가 없으면 새로운 브랜드가 추가됩니다.
            * brandId가 있으면 해당 ID의 브랜드가 수정됩니다.
            * 브랜드명은 중복될 수 없습니다.
            * 정렬 순서는 필수 입력값입니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "브랜드 추가/수정 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 (중복된 브랜드명, 필수값 누락 등)"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 접근"),
        @ApiResponse(responseCode = "403", description = "접근 권한 없음")
    })
    @PostMapping
    public ResponseEntity<CommonResponse<?>> addBrand(
        @Parameter(
            description = "브랜드 정보",
            required = true,
            example = """
                {
                    "brandId": 1,
                    "brandNm": "블루포트",
                    "orderNum": 2
                }
                """
        )
        @RequestBody BrandEntity brandEntity
    ) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(CommonResponse.of(brandService.addBrand(brandEntity)));
    }

    @Operation(
        summary = "브랜드 삭제",
        description = """
            지정된 ID의 브랜드를 삭제합니다.
            
            * 존재하지 않는 브랜드 ID를 입력하면 400 에러가 발생합니다.
            * 삭제된 브랜드는 목록 조회에서 제외됩니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "브랜드 삭제 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 (존재하지 않는 브랜드 ID 등)"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 접근"),
        @ApiResponse(responseCode = "403", description = "접근 권한 없음")
    })
    @DeleteMapping("/{brandId}")
    public ResponseEntity<CommonResponse<?>> deleteBrand(
        @Parameter(
            description = "브랜드 ID",
            required = true,
            example = "1"
        )
        @PathVariable Long brandId
    ) {
        brandService.deleteBrand(brandId);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(CommonResponse.of(null));
    }
}

