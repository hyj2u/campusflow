package com.cnco.campusflow.store;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.user.AppUserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
@Transactional
@Tag(
    name = "Store",
    description = """
        매장 관리 API
        
        * 매장 검색 및 조회 기능을 제공합니다.
        * 즐겨찾기 매장 관리 기능을 포함합니다.
        * JWT 인증이 필요한 API가 있습니다.
        """
)
public class StoreController {
    private final StoreService storeService;

    @Operation(
        summary = "매장 검색",
        description = """
            검색어와 위치 기반으로 매장을 검색합니다.
            
            * 검색어, 위도, 경도를 기준으로 매장을 검색합니다.
            * 페이지네이션을 지원합니다.
            * 거리순으로 정렬됩니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "매장 검색 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값")
    })
    @GetMapping
    public PaginatedResponse<StoreListDto> searchStores(
        @Parameter(description = "검색어") @RequestParam(required = false) String search,
        @Parameter(description = "위도", example = "37.5665") @RequestParam Double latitude,
        @Parameter(description = "경도", example = "126.9780") @RequestParam Double longitude,
        @Parameter(hidden = true) @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<StoreListDto> page = storeService.findStores(search, latitude, longitude, pageable);
        return new PaginatedResponse<>(
                page.getContent(),       // 데이터 목록
                page.getNumber(),        // 현재 페이지 번호
                page.getSize(),          // 페이지 크기
                page.getTotalElements(), // 전체 요소 수
                page.getTotalPages()     // 전체 페이지 수
        );
    }
    @Operation(
            summary = "매장 상세 조회",
            description = """
            선택한 매장정보를  조회합니다.
            
            * 선택한 매장 정보를 조회합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "즐겨찾기 매장 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @GetMapping("/{storeId}")
    public ResponseEntity<CommonResponse<?>> getMyStores(
            @Parameter(description = "매장 번호", example = "15") @PathVariable Long storeId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(storeService.getStoreDetail(storeId)));
    }

    @Operation(
        summary = "즐겨찾기 매장 조회",
        description = """
            인증된 사용자의 즐겨찾기 매장 목록을 조회합니다.
            
            * 사용자가 즐겨찾기한 매장 목록을 조회합니다.
            * 페이지네이션을 지원합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "즐겨찾기 매장 조회 성공"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @GetMapping("/favorite")
    public PaginatedResponse<StoreListDto> getMyStores(
        @Parameter(description = "인증된 사용자 정보") @AuthenticationPrincipal AppUserEntity user,
        @ParameterObject  @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<StoreListDto> page = storeService.getMyStores(user, pageable);
        return new PaginatedResponse<>(
                page.getContent(),       // 데이터 목록
                page.getNumber(),        // 현재 페이지 번호
                page.getSize(),          // 페이지 크기
                page.getTotalElements(), // 전체 요소 수
                page.getTotalPages()     // 전체 페이지 수
        );
    }

    @Operation(
        summary = "즐겨찾기 매장 추가",
        description = """
            특정 매장을 즐겨찾기에 추가합니다.
            
            * 매장 ID로 해당 매장을 즐겨찾기에 추가합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "즐겨찾기 매장 추가 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @PostMapping("/favorite/{storeId}")
    public ResponseEntity<CommonResponse<?>> addFavoriteStore(
        @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(description = "매장 번호", example = "15") @PathVariable Long storeId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(storeService.addFavoriteStore(appUser, storeId)));
    }

    @Operation(
        summary = "즐겨찾기 매장 삭제",
        description = """
            특정 매장을 즐겨찾기에서 삭제합니다.
            
            * 매장 ID로 해당 매장을 즐겨찾기에서 삭제합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "즐겨찾기 매장 삭제 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @DeleteMapping("/favorite/{storeId}")
    public ResponseEntity<CommonResponse<?>> removeFavoriteStore(
        @Parameter(description = "인증된 사용자 정보") @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(description = "매장 번호", example = "1") @PathVariable Long storeId
    ) {
        storeService.removeFavoriteStore(appUser, storeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
