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
@Tag(name = "Menu", description = "Menu management APIs")
public class MenuController {
    private final MenuService menuService;

    @Operation(summary = "Get categories by store", description = "Retrieves the categories for a specific store.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/categories/{storeId}")
    public ResponseEntity<CommonResponse<?>> getCategories(
        @Parameter(description = "Store ID") @PathVariable Long storeId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(menuService.getCategoriesByStore(storeId)));
    }

    @Operation(summary = "Get menus by category", description = "Retrieves the menus for a specific category.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Menus retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/{categoryId}")
    public ResponseEntity<CommonResponse<?>> getMenus(
        @Parameter(description = "Category ID") @PathVariable Long categoryId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(menuService.getMenus(categoryId)));
    }

    @Operation(summary = "Get product option groups", description = "Retrieves the option groups for a specific product.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product option groups retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/{productId}/dtl")
    public ResponseEntity<?> getProductOptionGroups(
        @Parameter(description = "Product ID") @PathVariable Long productId
    ) {
        List<OptGrpResponseDto> result = menuService.getProductOptionGroups(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(result));
    }

    @Operation(summary = "Add favorite menu", description = "Adds a menu to the user's favorite list.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Menu added to favorites successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/favorite")
    public ResponseEntity<?> addFavoriteMenu(
        @Parameter(description = "Authenticated user details") @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(description = "Menu request details") @RequestBody MenuRequestDto menuRequestDto
    ) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(menuService.addFavoriteMenu(menuRequestDto, appUser)));
    }

    @Operation(summary = "Get favorite menu", description = "Retrieves the favorite menu for a specific store and user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Favorite menu retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/favorite/{storeId}")
    public ResponseEntity<?> getFavoriteMenu(
        @Parameter(description = "Authenticated user details") @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(description = "Store ID") @PathVariable Long storeId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(menuService.getFavoriteMenu(storeId, appUser)));
    }

    @Operation(summary = "Delete favorite menu", description = "Removes a menu from the user's favorite list.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Menu removed from favorites successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @DeleteMapping("/favorite/{favoriteMenuId}")
    public ResponseEntity<?> deleteFavoriteMenu(
        @Parameter(description = "Favorite menu ID") @PathVariable Long favoriteMenuId
    ) {
        menuService.deleteFavoriteMenu(favoriteMenuId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Add cart menu", description = "Adds a menu to the user's cart.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Menu added to cart successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/cart")
    public ResponseEntity<?> addCartMenu(
        @Parameter(description = "Menu request details") @RequestBody MenuRequestDto menuRequestDto
    ) throws JsonProcessingException {
        Map<String, Object> data = new HashMap<>();
        data.put("menuId", menuService.addCartMenu(menuRequestDto).getMenuId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(data));
    }

    @Operation(summary = "Change cart menu", description = "Updates a menu in the user's cart.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cart menu updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/cart/{menuId}")
    public ResponseEntity<?> changeCartMenu(
        @Parameter(description = "Menu ID") @PathVariable Long menuId,
        @Parameter(description = "Menu request details") @RequestBody MenuRequestDto menuRequestDto
    ) throws JsonProcessingException {
        menuService.changeCartMenu(menuId, menuRequestDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete cart menu", description = "Removes a menu from the user's cart.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Menu removed from cart successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @DeleteMapping("/cart/{menuId}")
    public ResponseEntity<?> deleteCartMenu(
        @Parameter(description = "Menu ID") @PathVariable Long menuId
    ) throws JsonProcessingException {
        menuService.deleteCartMenu(menuId);
        return ResponseEntity.noContent().build();
    }
}
