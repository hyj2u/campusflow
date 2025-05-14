package com.cnco.campusflow.menu;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.optgrp.OptGrpResponseDto;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
@Transactional
@Tag(
    name = "Menu",
    description = """
        메뉴 관리 API
        
        * 매장별 메뉴 조회 및 관리 기능을 제공합니다.
        * 카테고리별 메뉴 조회, 즐겨찾기, 장바구니 기능을 포함합니다.
        * JWT 인증이 필요한 API입니다.
        """
)
public class MenuController {
    private final MenuService menuService;

    @Operation(
        summary = "매장별 카테고리 조회",
        description = """
            매장의 카테고리 목록을 조회합니다.
            
            * 매장 ID로 해당 매장의 카테고리를 조회합니다.
            * 카테고리별 메뉴를 조회할 수 있습니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "카테고리 조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
        @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음")
    })
    @GetMapping("/categories/{storeId}")
    public ResponseEntity<CommonResponse<?>> getCategories(
        @Parameter(description = "매장 번호", example = "1") @PathVariable Long storeId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(menuService.getCategoriesByStore(storeId)));
    }

    @Operation(
        summary = "카테고리별 메뉴 조회",
        description = """
            카테고리의 메뉴 목록을 조회합니다.
            
            * 카테고리 ID로 해당 카테고리의 메뉴를 조회합니다.
            * 메뉴의 기본 정보와 옵션 정보를 포함합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "메뉴 조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
        @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음")
    })
    @GetMapping("/{categoryId}")
    public ResponseEntity<CommonResponse<?>> getMenus(
        @Parameter(description = "카테고리 번호", example = "24") @PathVariable Long categoryId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(menuService.getMenus(categoryId)));
    }

    @Operation(
        summary = "상품 옵션 그룹 조회",
        description = """
            상품의 옵션 그룹 목록을 조회합니다.
            
            * 상품 ID로 해당 상품의 옵션 그룹을 조회합니다.
            * 옵션 그룹별 상세 옵션 정보를 포함합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "옵션 그룹 조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
        @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    @GetMapping("/{productId}/dtl")
    public ResponseEntity<?> getProductOptionGroups(
        @Parameter(description = "상품 번호", example = "1") @PathVariable Long productId
    ) {
        List<OptGrpResponseDto> result = menuService.getProductOptionGroups(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(result));
    }

    @Operation(
        summary = "즐겨찾기 메뉴 추가",
        description = """
            사용자의 즐겨찾기 메뉴를 추가합니다.
            
            * 상품 ID와 옵션 정보로 즐겨찾기 메뉴를 생성합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "즐겨찾기 메뉴 추가 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @PostMapping("/favorite")
    public ResponseEntity<?> addFavoriteMenu(
        @Parameter(description = "인증된 사용자 정보") @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(description = "메뉴 요청 정보") @RequestBody MenuRequestDto menuRequestDto
    ) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(menuService.addFavoriteMenu(menuRequestDto, appUser)));
    }

    @Operation(
        summary = "즐겨찾기 메뉴 조회",
        description = """
            매장별 사용자의 즐겨찾기 메뉴를 조회합니다.
            
            * 매장 ID와 사용자 정보로 즐겨찾기 메뉴를 조회합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "즐겨찾기 메뉴 조회 성공"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음")
    })
    @GetMapping("/favorite/{storeId}")
    public ResponseEntity<?> getFavoriteMenu(
        @Parameter(description = "인증된 사용자 정보") @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(description = "매장 번호", example = "42") @PathVariable Long storeId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(menuService.getFavoriteMenu(storeId, appUser)));
    }

    @Operation(
        summary = "즐겨찾기 메뉴 삭제",
        description = """
            사용자의 즐겨찾기 메뉴를 삭제합니다.
            
            * 즐겨찾기 메뉴 ID로 해당 메뉴를 삭제합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "즐겨찾기 메뉴 삭제 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @ApiResponse(responseCode = "404", description = "즐겨찾기 메뉴를 찾을 수 없음")
    })
    @DeleteMapping("/favorite/{favoriteMenuId}")
    public ResponseEntity<?> deleteFavoriteMenu(
        @Parameter(description = "즐겨찾기 메뉴 번호", example = "1") @PathVariable Long favoriteMenuId
    ) {
        menuService.deleteFavoriteMenu(favoriteMenuId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
        summary = "장바구니 메뉴 추가",
        description = """
            사용자의 장바구니에 메뉴를 추가합니다.
            
            * 상품 ID와 옵션 정보로 장바구니 메뉴를 생성합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "장바구니 메뉴 추가 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @PostMapping("/cart")
    public ResponseEntity<?> addCartMenu(
        @Parameter(description = "메뉴 요청 정보") @RequestBody MenuRequestDto menuRequestDto
    ) throws JsonProcessingException {
        Map<String, Object> data = new HashMap<>();
        data.put("menuId", menuService.addCartMenu(menuRequestDto).getMenuId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(data));
    }

    @Operation(
        summary = "장바구니 메뉴 수정",
        description = """
            사용자의 장바구니 메뉴를 수정합니다.
            
            * 메뉴 ID와 새로운 옵션 정보로 장바구니 메뉴를 수정합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "장바구니 메뉴 수정 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @ApiResponse(responseCode = "404", description = "장바구니 메뉴를 찾을 수 없음")
    })
    @PutMapping("/cart/{menuId}")
    public ResponseEntity<?> changeCartMenu(
        @Parameter(description = "메뉴 번호", example = "1") @PathVariable Long menuId,
        @Parameter(description = "메뉴 요청 정보") @RequestBody MenuRequestDto menuRequestDto
    ) throws JsonProcessingException {
        menuService.changeCartMenu(menuId, menuRequestDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "장바구니 메뉴 삭제",
        description = """
            사용자의 장바구니 메뉴를 삭제합니다.
            
            * 메뉴 ID로 해당 장바구니 메뉴를 삭제합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "장바구니 메뉴 삭제 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @ApiResponse(responseCode = "404", description = "장바구니 메뉴를 찾을 수 없음")
    })
    @DeleteMapping("/cart/{menuId}")
    public ResponseEntity<?> deleteCartMenu(
        @Parameter(description = "메뉴 번호", example = "1") @PathVariable Long menuId
    ) throws JsonProcessingException {
        menuService.deleteCartMenu(menuId);
        return ResponseEntity.noContent().build();
    }
}
