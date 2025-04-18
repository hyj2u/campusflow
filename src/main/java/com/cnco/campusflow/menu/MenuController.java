package com.cnco.campusflow.menu;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.optgrp.OptGrpResponseDto;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/categories/{storeId}")
    public ResponseEntity<CommonResponse<?>> getCategories(@PathVariable Long storeId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(menuService.getCategoriesByStore(storeId)));
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<CommonResponse<?>> getMenus(@PathVariable Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(menuService.getMenus(categoryId)));
    }

    @GetMapping("/{productId}/dtl")
    public ResponseEntity<?> getProductOptionGroups(@PathVariable Long productId) {
        List<OptGrpResponseDto> result = menuService.getProductOptionGroups(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(result));
    }
    @PostMapping("/favorite")
    public ResponseEntity<?> addFavoriteMenu(@AuthenticationPrincipal AppUserEntity appUser, @RequestBody MenuRequestDto menuRequestDto)
            throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(menuService.addFavoriteMenu(menuRequestDto,appUser)));
    }
    @GetMapping("/favorite/{storeId}")
    public ResponseEntity<?>getFavoriteMenu(@AuthenticationPrincipal AppUserEntity appUser, @PathVariable Long storeId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(menuService.getFavoriteMenu(storeId,appUser)));
    }
    @DeleteMapping("/favorite/{favoriteMenuId}")
    public ResponseEntity<?>deleteFavoriteMenu( @PathVariable Long favoriteMenuId) {
        menuService.deleteFavoriteMenu(favoriteMenuId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PostMapping("/cart")
    public ResponseEntity<?> addCartMenu(@RequestBody MenuRequestDto menuRequestDto)
            throws JsonProcessingException {
        Map<String, Object> data = new HashMap<>();
        data.put("menuId", menuService.addCartMenu(menuRequestDto).getMenuId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(data));
    }
    @PutMapping("/cart/{menuId}")
    public ResponseEntity<?> changeCartMenu(@PathVariable Long menuId, @RequestBody MenuRequestDto menuRequestDto)
            throws JsonProcessingException {
        menuService.changeCartMenu(menuId, menuRequestDto);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/cart/{menuId}")
    public ResponseEntity<?> deleteCartMenu(@PathVariable Long menuId)
            throws JsonProcessingException {
        menuService.deleteCartMenu(menuId);
        return ResponseEntity.noContent().build();
    }





}
