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
@Tag(name = "Store", description = "Store management APIs")
public class StoreController {
    private final StoreService storeService;

    @Operation(summary = "Search stores", description = "Searches for stores based on the provided criteria.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stores retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping
    public PaginatedResponse<StoreListDto> searchStores(
        @Parameter(description = "Search term") @RequestParam(required = false) String search,
        @Parameter(description = "Latitude") @RequestParam Double latitude,
        @Parameter(description = "Longitude") @RequestParam Double longitude,
        @PageableDefault(size = 10) Pageable pageable
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

    @Operation(summary = "Get favorite stores", description = "Retrieves the list of favorite stores for the authenticated user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Favorite stores retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/favorite")
    public PaginatedResponse<StoreListDto> getMyStores(
        @Parameter(description = "Authenticated user details") @AuthenticationPrincipal AppUserEntity user,
        @PageableDefault(size = 10) Pageable pageable
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

    @Operation(summary = "Add favorite store", description = "Adds a store to the user's favorite list.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Store added to favorites successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/favorite/{storeId}")
    public ResponseEntity<CommonResponse<?>> addFavoriteStore(
        @Parameter(description = "Authenticated user details") @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(description = "Store ID") @PathVariable Long storeId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(storeService.addFavoriteStore(appUser, storeId)));
    }

    @Operation(summary = "Remove favorite store", description = "Removes a store from the user's favorite list.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Store removed from favorites successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @DeleteMapping("/favorite/{storeId}")
    public ResponseEntity<CommonResponse<?>> removeFavoriteStore(
        @Parameter(description = "Authenticated user details") @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(description = "Store ID") @PathVariable Long storeId
    ) {
        storeService.removeFavoriteStore(appUser, storeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
