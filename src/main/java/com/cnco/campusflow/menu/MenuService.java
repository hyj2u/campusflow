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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    private final ObjectMapper objectMapper;
    private final MenuRepository menuRepository;
    private final CartRepository cartRepository;

    public List<CategoryResponseDto> getCategoriesByStore(Long storeId) {
        return categoryRepository.findAllByStoreStoreId(storeId)
                .stream()
                .map(this::convertCategoryToDto)
                .collect(Collectors.toList());
    }

    public List<CategoryMenuResponseDto> getMenus(Long storeId) {
        List<CategoryEntity> categories = categoryRepository.findAllByStoreStoreId(storeId);

        return categories.stream().map(category -> {
            List<ProductEntity> products = productRepository
                    .findByCategoriesCategoryIdOrderByInsertTimestampDesc(category.getCategoryId());

            List<MenuProductResponseDto> menuList = products.stream()
                    .map(this::convertProductToDto)
                    .collect(Collectors.toList());

            CategoryMenuResponseDto dto = new CategoryMenuResponseDto();
            dto.setCategoryId(category.getCategoryId());
            dto.setCategoryNm(category.getCategoryNm());
            dto.setOrderNum(category.getOrderNum());
            dto.setBrandId(category.getBrand().getBrandId());
            dto.setBrandNm(category.getBrand().getBrandNm());
            dto.setMenuList(menuList);
            return dto;
        }).collect(Collectors.toList());
    }
    public List<MenuProductResponseDto> getRecommendMenus(Long storeId) {
        List<ProductEntity> products = productRepository.findAllByStoreStoreIdAndActiveYnOrderByInsertTimestampDesc(storeId, "Y")
                .stream()
                .filter(product -> product.getProductTags().stream()
                        .anyMatch(tag -> {
                            String tagNm = tag.getProductTagNm();
                            return "BEST".equalsIgnoreCase(tagNm) || "HOT".equalsIgnoreCase(tagNm);
                        })
                )
                .collect(Collectors.toList());

        return products.stream()
                .map(this::convertProductToDto)
                .collect(Collectors.toList());
    }

    public MenuProductDtlResponseDto getProductDetail(Long productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        List<OptGrpEntity> optGrps = new ArrayList<>(product.getOptGrp());
        List<OptionEntity> options = optionRepository.findByOptGrpIn(optGrps);

        Map<Long, List<OptionEntity>> groupMap = options.stream()
                .collect(Collectors.groupingBy(opt -> opt.getOptGrp().getOptGrpId()));

        List<OptGrpResponseDto> optGrpDtos = optGrps.stream()
                .map(grp -> {
                    OptGrpResponseDto grpDto = new OptGrpResponseDto();
                    grpDto.setOptGrpId(grp.getOptGrpId());
                    grpDto.setOptGrpNm(grp.getOptGrpNm());
                    grpDto.setOrderNum(grp.getOrderNum());

                    List<OptionResponseDto> optionDtos = groupMap.getOrDefault(grp.getOptGrpId(), List.of())
                            .stream()
                            .map(this::toOptionDto)
                            .collect(Collectors.toList());

                    grpDto.setOptions(optionDtos);
                    return grpDto;
                }).collect(Collectors.toList());

        // 메인 이미지 URL 생성
        String imageUrl = null;
        if (product.getMainImg() != null) {
            imageUrl = imageBaseUrl + "/" + product.getMainImg().getImageId();
        }

        return new MenuProductDtlResponseDto(
                product.getProductId(),
                product.getProductNm(),
                product.getPrice(),
                imageUrl,
                optGrpDtos
        );
    }
    public FavoriteMenuResponseDto addFavoriteMenu(MenuRequestDto menuRequestDto, AppUserEntity appUser) throws JsonProcessingException {
        log.info(objectMapper.writeValueAsString(menuRequestDto));

        List<MenuOptionEntity> options = buildMenuOptions(menuRequestDto);
        ProductEntity product = productRepository.findById(menuRequestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다. ID: " + menuRequestDto.getProductId()));

        MenuEntity menu = new MenuEntity();
        menu.setProduct(product);
        menu.setStore(product.getStore());
        menu.setOptions(options);
        menu = menuRepository.save(menu);

        FavoriteMenuEntity favoriteMenu = new FavoriteMenuEntity();
        favoriteMenu.setMenu(menu);
        favoriteMenu.setUser(appUser);
        favoriteMenu = favoriteMenuRepository.save(favoriteMenu);

        return toFavoriteMenuDto(favoriteMenu);
    }

    public MenuEntity addCartMenu(MenuRequestDto menuRequestDto, AppUserEntity appUser) throws JsonProcessingException {
        log.info(objectMapper.writeValueAsString(menuRequestDto));

        List<MenuOptionEntity> options = buildMenuOptions(menuRequestDto);
        ProductEntity product = productRepository.findById(menuRequestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다. ID: " + menuRequestDto.getProductId()));

        MenuEntity menu = new MenuEntity();
        menu.setProduct(product);
        menu.setStore(product.getStore());
        menu.setOptions(options);
        menu.setOrderCnt(menuRequestDto.getOrderCnt());
        MenuEntity menuEntity = menuRepository.save(menu);
        CartEntity cart;
        if (cartRepository.findByAppUser(appUser) != null) {
            cart = cartRepository.findByAppUser(appUser);
            cart.getMenus().add(menuEntity);
        } else {
            cart = new CartEntity();
            List<MenuEntity> menuEntities = new ArrayList<>();
            menuEntities.add(menuEntity);
            cart.setMenus(menuEntities);
        }
        cart.setAppUser(appUser);
        cartRepository.save(cart);
        return menuEntity;

    }
    public CartResponseDto getCartList( AppUserEntity appUser)  {
        CartEntity cart = cartRepository.findByAppUser(appUser);
        if (cart == null) {
            throw new EntityNotFoundException("장바구니가 존재하지 않습니다.");
        };
        return toCartResponseDto(cart);
    }

    public void changeCartMenu(Long menuId, MenuRequestDto menuRequestDto) throws JsonProcessingException {
        log.info(objectMapper.writeValueAsString(menuRequestDto));
        MenuEntity menu = menuRepository.findById(menuId).get();
        // 새 옵션을 빌드
        List<MenuOptionEntity> options = buildMenuOptions(menuRequestDto);
        // 기존 컬렉션 비우고, 새 옵션 추가
        menu.getOptions().clear();
        menu.getOptions().addAll(options);
        menu.setOrderCnt(menuRequestDto.getOrderCnt());
        menuRepository.save(menu);
    }

    public void deleteCartMenu(Long menuId) throws JsonProcessingException {
        menuRepository.deleteById(menuId);
    }
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }



    public List<FavoriteMenuResponseDto> getFavoriteMenu(Long storeId, AppUserEntity appUser) {
        List<FavoriteMenuEntity> favoriteMenuEntities =
                favoriteMenuRepository.findAllByUserAppUserIdAndMenuStoreStoreId(appUser.getAppUserId(), storeId);
        return toFavoriteMenuDtoList(favoriteMenuEntities);
    }

    public void deleteFavoriteMenu(Long FavoriteMenuId) {
        favoriteMenuRepository.deleteById(FavoriteMenuId);
    }

    private List<FavoriteMenuResponseDto> toFavoriteMenuDtoList(List<FavoriteMenuEntity> entities) {
        List<FavoriteMenuResponseDto> responseList = new ArrayList<>();
        for (FavoriteMenuEntity favorite : entities) {
            FavoriteMenuResponseDto response = new FavoriteMenuResponseDto();
            response.setFavMenuId(favorite.getFavMenuId());

            MenuEntity menu = favorite.getMenu();
            MenuResponseDto menuDto = new MenuResponseDto();
            menuDto.setMenuId(menu.getMenuId());
            menuDto.setStoreId(menu.getStore().getStoreId());
            menuDto.setStoreName(menu.getStore().getStoreNm());
            menuDto.setProductId(menu.getProduct().getProductId());
            menuDto.setProductName(menu.getProduct().getProductNm());

            List<MenuOptionDto> optionDtos = new ArrayList<>();
            for (MenuOptionEntity option : menu.getOptions()) {
                MenuOptionDto optionDto = new MenuOptionDto();
                optionDto.setOptionId(option.getOptionEntity().getOptionId());
                optionDto.setOptionNm(option.getOptionEntity().getOptionNm());
                optionDto.setChosenNum(option.getChosenNum());
                optionDto.setTotalPrice(option.getTotalPrice());
                optionDto.setMenuOptId(option.getMenuOptId());
                optionDto.setMin(option.getOptDtlEntity().getMin());
                optionDto.setMax(option.getOptDtlEntity().getMax());
                optionDto.setPrice(option.getOptDtlEntity().getPrice());
                optionDto.setType(option.getOptDtlEntity().getType());
                optionDto.setCodeNm(option.getOptionEntity().getCode().getCodeNm());
                optionDto.setOptDtlId(option.getOptDtlEntity().getOptDtlId());
                optionDto.setOptDtlNm(option.getOptDtlEntity().getOpDtlNm());
                optionDto.setUnitPrice(option.getOptDtlEntity().getUnitPrice());
                optionDtos.add(optionDto);
            }

            menuDto.setOptions(optionDtos);
            response.setMenu(menuDto);
            responseList.add(response);
        }
        return responseList;
    }


    private List<MenuOptionEntity> buildMenuOptions(MenuRequestDto dto) {
        List<MenuOptionEntity> result = new ArrayList<>();
        if (dto.getOptions() == null) return result;

        for (MenuOptionRequestDto req : dto.getOptions()) {
            OptionEntity option = optionRepository.findById(req.getOptionId())
                    .orElseThrow(() -> new IllegalArgumentException("옵션이 존재하지 않습니다. ID: " + req.getOptionId()));
            OptDtlEntity optDtl = optDtlRepository.findById(req.getOptDtlId())
                    .orElseThrow(() -> new IllegalArgumentException("옵션 상세가 존재하지 않습니다. ID: " + req.getOptDtlId()));

            MenuOptionEntity menuOption = new MenuOptionEntity();
            menuOption.setOptionEntity(option);
            menuOption.setChosenNum(req.getChosenNum());
            menuOption.setTotalPrice(req.getTotalPrice());
            menuOption.setOptDtlEntity(optDtl);
            result.add(menuOption);
        }
        return result;
    }

    private FavoriteMenuResponseDto toFavoriteMenuDto(FavoriteMenuEntity favoriteMenu) {
        FavoriteMenuResponseDto response = new FavoriteMenuResponseDto();
        response.setFavMenuId(favoriteMenu.getFavMenuId());

        MenuEntity menu = favoriteMenu.getMenu();
        MenuResponseDto menuDto = new MenuResponseDto();
        menuDto.setMenuId(menu.getMenuId());
        menuDto.setStoreId(menu.getStore().getStoreId());
        menuDto.setStoreName(menu.getStore().getStoreNm());
        menuDto.setProductId(menu.getProduct().getProductId());
        menuDto.setProductName(menu.getProduct().getProductNm());

        List<MenuOptionDto> optionDtos = menu.getOptions().stream()
                .map(this::toMenuOptionDto)
                .collect(Collectors.toList());
        menuDto.setOptions(optionDtos);

        response.setMenu(menuDto);
        return response;
    }

    private MenuOptionDto toMenuOptionDto(MenuOptionEntity option) {
        MenuOptionDto dto = new MenuOptionDto();
        dto.setOptionId(option.getOptionEntity().getOptionId());
        dto.setOptionNm(option.getOptionEntity().getOptionNm());
        dto.setChosenNum(option.getChosenNum());
        dto.setTotalPrice(option.getTotalPrice());
        dto.setMenuOptId(option.getMenuOptId());
        dto.setMin(option.getOptDtlEntity().getMin());
        dto.setMax(option.getOptDtlEntity().getMax());
        dto.setPrice(option.getOptDtlEntity().getPrice());
        dto.setType(option.getOptDtlEntity().getType());
        dto.setCodeNm(option.getOptionEntity().getCode().getCodeNm());
        dto.setOptDtlId(option.getOptDtlEntity().getOptDtlId());
        dto.setOptDtlNm(option.getOptDtlEntity().getOpDtlNm());
        dto.setUnitPrice(option.getOptDtlEntity().getUnitPrice());
        return dto;
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
            dto.setMainImageUrl(imageBaseUrl + "/" + entity.getMainImg().getImageId());
        }
        return dto;
    }

    private OptionResponseDto toOptionDto(OptionEntity option) {
        OptionResponseDto dto = new OptionResponseDto();
        dto.setOptionId(option.getOptionId());
        dto.setOptionNm(option.getOptionNm());
        dto.setCodeNm(option.getCode().getCodeNm());
        dto.setRequireYn(option.getRequireYn());
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
                    d.setDtlUseYn(dtl.getDtlUseYn());
                    return d;
                })
                .collect(Collectors.toList());

        dto.setDetails(details);
        return dto;
    }

    private CartResponseDto toCartResponseDto(CartEntity cart) {
        CartResponseDto dto = new CartResponseDto();
        dto.setCartId(cart.getCartId());

        List<MenuResponseDto> menus = cart.getMenus().stream()
                .map(menu -> {
                    MenuResponseDto menuDto = new MenuResponseDto();
                    menuDto.setMenuId(menu.getMenuId());
                    menuDto.setStoreId(menu.getStore().getStoreId());
                    menuDto.setStoreName(menu.getStore().getStoreNm());
                    menuDto.setProductId(menu.getProduct().getProductId());
                    menuDto.setProductName(menu.getProduct().getProductNm());
                    menuDto.setOrderCnt(menu.getOrderCnt());
                    List<MenuOptionDto> optionDtos = menu.getOptions().stream()
                            .map(this::toMenuOptionDto)
                            .collect(Collectors.toList());

                    menuDto.setOptions(optionDtos);
                    return menuDto;
                })
                .collect(Collectors.toList());

        dto.setMenus(menus);
        return dto;
    }
}
