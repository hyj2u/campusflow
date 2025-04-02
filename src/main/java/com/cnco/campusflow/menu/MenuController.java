package com.cnco.campusflow.menu;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.optgrp.OptGrpResponseDto;
import com.cnco.campusflow.user.AppUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> addFavoriteMenu(@AuthenticationPrincipal AppUserEntity appUser, MenuRequestDto menuRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(menuService.addFavoriteMenu(menuRequestDto,appUser)));
    }



}
