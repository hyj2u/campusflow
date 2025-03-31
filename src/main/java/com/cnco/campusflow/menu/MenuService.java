package com.cnco.campusflow.menu;

import com.cnco.campusflow.category.CategoryEntity;
import com.cnco.campusflow.category.CategoryRepository;
import com.cnco.campusflow.category.CategoryResponseDto;
import com.cnco.campusflow.product.ProductEntity;
import com.cnco.campusflow.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {


    @Value("${image.base.path}")
    private String imageBasePath;
    @Value("${image.base.url}")
    private String imageBaseUrl;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    public List<CategoryResponseDto> getCategoriesByStore(Long storeId) {
        return categoryRepository.findAllByStoreStoreId(storeId)
                .stream()
                .map(this::convertCategoryToDto)
                .collect(Collectors.toList());
    }
    public List<MenuProductResponseDto> getMenus(Long categoryId) {
        return productRepository.findByCategoriesCategoryIdOrderByInsertTimestampDesc(categoryId)
                .stream()
                .map(this::convertProductToDto)
                .collect(Collectors.toList());
    }
    private CategoryResponseDto convertCategoryToDto(CategoryEntity entity) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setCategoryId(entity.getCategoryId());
        dto.setCategoryNm(entity.getCategoryNm());
        dto.setOrderNum(entity.getOrderNum());
        dto.setBrandId(entity.getBrand().getBrandId());
        dto.setBrandNm(entity.getBrand().getBrandNm());
        return dto;
    }
    private MenuProductResponseDto convertProductToDto(ProductEntity entity) {
        MenuProductResponseDto dto = new MenuProductResponseDto();
        dto.setProductId(entity.getProductId());
        dto.setProductNm(entity.getProductNm());
        dto.setPrice(entity.getPrice());
        if (entity.getMainImg() != null) {
            dto.setMainImageUrl(imageBaseUrl+"/"+entity.getMainImg().getImageId()); // 또는 imgNm + 경로 조합
        }
        return dto;
    }

}
