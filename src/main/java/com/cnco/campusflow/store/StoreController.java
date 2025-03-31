package com.cnco.campusflow.store;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.user.AppUserEntity;
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
public class StoreController {
    private final StoreService storeService;

    @GetMapping
    public PaginatedResponse<StoreListDto> searchStores(@RequestParam(required = false) String search,@RequestParam Double latitude, @RequestParam Double longitude ,
                                                        @PageableDefault(size = 10) Pageable pageable) {
        Page<StoreListDto> page = storeService.findStores(search, latitude, longitude, pageable);
        return new PaginatedResponse<>(
                page.getContent(),       // 데이터 목록
                page.getNumber(),        // 현재 페이지 번호
                page.getSize(),          // 페이지 크기
                page.getTotalElements(), // 전체 요소 수
                page.getTotalPages()     // 전체 페이지 수
        );
    }

    @GetMapping("/favorite")
    public PaginatedResponse<StoreListDto> getMyStores(@AuthenticationPrincipal AppUserEntity user, @PageableDefault(size = 10) Pageable pageable) {
        Page<StoreListDto> page = storeService.getMyStores(user,pageable);
        return new PaginatedResponse<>(
                page.getContent(),       // 데이터 목록
                page.getNumber(),        // 현재 페이지 번호
                page.getSize(),          // 페이지 크기
                page.getTotalElements(), // 전체 요소 수
                page.getTotalPages()     // 전체 페이지 수
        );
    }
    @PostMapping("/favorite/{storeId}")
    public ResponseEntity<CommonResponse<?>> addFavoriteStore(@AuthenticationPrincipal AppUserEntity appUser, @PathVariable Long storeId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(storeService.addFavoriteStore(appUser, storeId)));
    }
    @DeleteMapping("/favorite/{storeId}")
    public  ResponseEntity<CommonResponse<?>>  removeFavoriteStore(@AuthenticationPrincipal AppUserEntity appUser, @PathVariable Long storeId) {
        storeService.removeFavoriteStore(appUser, storeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }




}
