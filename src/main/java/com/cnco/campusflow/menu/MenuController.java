package com.cnco.campusflow.menu;

import com.cnco.campusflow.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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





}
