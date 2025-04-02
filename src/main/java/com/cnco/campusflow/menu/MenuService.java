package com.cnco.campusflow.menu;

import com.cnco.campusflow.category.CategoryEntity;
import com.cnco.campusflow.category.CategoryRepository;
import com.cnco.campusflow.category.CategoryResponseDto;
import com.cnco.campusflow.optdtl.OptDtlEntity;
import com.cnco.campusflow.optdtl.OptDtlRepository;
import com.cnco.campusflow.optdtl.OptDtlResponseDto;
import com.cnco.campusflow.optgrp.OptGrpEntity;
import com.cnco.campusflow.optgrp.OptGrpResponseDto;
import com.cnco.campusflow.option.OptionEntity;
import com.cnco.campusflow.option.OptionRepository;
import com.cnco.campusflow.option.OptionResponseDto;
import com.cnco.campusflow.product.ProductEntity;
import com.cnco.campusflow.product.ProductRepository;
import com.cnco.campusflow.user.AppUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private final OptionRepository optionRepository;
    private final OptDtlRepository optDtlRepository;
    private final FavoriteMenuRepository favoriteMenuRepository;


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
    public List<OptGrpResponseDto> getProductOptionGroups(Long productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        List<OptGrpEntity> optGrps = new ArrayList<>(product.getOptGrp());
        List<OptionEntity> options = optionRepository.findByOptGrpIn(optGrps);

        // 그룹별 옵션 맵핑
        Map<Long, List<OptionEntity>> groupMap = options.stream()
                .collect(Collectors.groupingBy(opt -> opt.getOptGrp().getOptGrpId()));

        return optGrps.stream()
                .map(grp -> {
                    OptGrpResponseDto grpDto = new OptGrpResponseDto();
                    grpDto.setOptGrpId(grp.getOptGrpId());
                    grpDto.setOptGrpNm(grp.getOptGrpNm());
                    grpDto.setOrderNum(grp.getOrderNum());
                    List<OptionResponseDto> optionDtos = groupMap.getOrDefault(grp.getOptGrpId(), List.of()).stream()
                            .map(this::toOptionDto)
                            .collect(Collectors.toList());

                    grpDto.setOptions(optionDtos);
                    return grpDto;
                })
                .collect(Collectors.toList());
    }
    private OptionResponseDto toOptionDto(OptionEntity option) {
        OptionResponseDto dto = new OptionResponseDto();
        dto.setOptionId(option.getOptionId());
        dto.setOptionNm(option.getOptionNm());
        dto.setCodeNm(option.getCode().getCodeNm());
        List<OptDtlResponseDto> details = option.getOptions().stream()
                .map(dtl -> {
                    OptDtlResponseDto d = new OptDtlResponseDto();
                    d.setOptDtlId(dtl.getOptDtlId());
                    d.setOpDtlNm(dtl.getOpDtlNm());
                    d.setPrice(dtl.getPrice());
                    d.setType(dtl.getType());
                    d.setMin(dtl.getMin());
                    d.setMax(dtl.getMax());
                    d.setUnitPrice(dtl.getUnitPrice());
                    d.setPrice(dtl.getPrice());
                    return d;
                })
                .collect(Collectors.toList());

        dto.setDetails(details);
        return dto;
    }
    public FavoriteMenuEntity addFavoriteMenu(MenuRequestDto menuRequestDto, AppUserEntity appUser) {
        // 1. 옵션 처리
        List<MenuOptionEntity> options = new ArrayList<>();
        if(menuRequestDto.getOptions() != null) {
            for (MenuOptionRequestDto dto : menuRequestDto.getOptions()) {
                OptionEntity option = optionRepository.findById(dto.getOptionId())
                        .orElseThrow(() -> new IllegalArgumentException("옵션이 존재하지 않습니다. ID: " + dto.getOptionId()));

                OptDtlEntity optDtl = optDtlRepository.findById(dto.getOptDtlId())
                        .orElseThrow(() -> new IllegalArgumentException("옵션 상세가 존재하지 않습니다. ID: " + dto.getOptDtlId()));

                MenuOptionEntity menuOption = new MenuOptionEntity();
                menuOption.setOptionEntity(option);
                menuOption.setChosenNum(dto.getChosenNum());
                menuOption.setTotalPrice(dto.getTotalPrice());
                menuOption.setOptDtlEntity(optDtl);

                options.add(menuOption);
            }
        }

        // 2. 상품 정보 및 메뉴 생성
        ProductEntity product = productRepository.findById(menuRequestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다. ID: " + menuRequestDto.getProductId()));

        MenuEntity menu = new MenuEntity();
        menu.setProduct(product);
        menu.setStore(product.getStore());
        menu.setOptions(options);

        // 3. 즐겨찾기 메뉴 엔티티 생성 및 저장
        FavoriteMenuEntity favoriteMenu = new FavoriteMenuEntity();
        favoriteMenu.setMenu(menu);
        favoriteMenu.setUser(appUser);

        return favoriteMenuRepository.save(favoriteMenu);
    }
}
