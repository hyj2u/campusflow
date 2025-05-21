package com.cnco.campusflow.banner;

import com.cnco.campusflow.bannertype.BannerTypeEntity;
import com.cnco.campusflow.bannertype.BannerTypeRepository;
import com.cnco.campusflow.common.FileUtil;
import com.cnco.campusflow.image.ImageEntity;
import com.cnco.campusflow.image.ImageRepository;
import com.cnco.campusflow.store.StoreEntity;
import com.cnco.campusflow.store.StoreRepository;
import com.cnco.campusflow.code.CodeEntity;
import com.cnco.campusflow.code.CodeRepository;
import com.cnco.campusflow.brand.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BannerService {

    private final BannerRepository bannerRepository;
    private final BannerTypeRepository bannerTypeRepository;
    private final ImageRepository imageRepository;
    private final StoreRepository storeRepository;
    private final CodeRepository codeRepository;
    private final BrandRepository brandRepository;
    private final FileUtil fileUtill;

    @Value("${image.base.path}")
    private String imageBasePath;

    @Value("${image.base.url}")
    private String imageBaseUrl;

    /**
     * 배너 목록을 조회합니다.
     */
    public Page<BannerResponseDto> getBanners(Long storeId, String bannerTypeCd,Pageable pageable) {
        log.info("배너 목록 조회 시작 - storeId: {}, bannerTypeCd: {}", storeId, bannerTypeCd);
        
        Page<BannerResponseDto> banners = bannerRepository.findBanners(storeId, bannerTypeCd, pageable);
        
        List<BannerResponseDto> content = banners.getContent().stream().map(dto -> {
            if (dto.getImgPath() != null) {
                dto.setImgFullPath(imageBaseUrl + "/" + dto.getImgPath());
            }
            return dto;
        }).toList();

        log.info("배너 목록 조회 완료 - 총 {}건", banners.getTotalElements());
        return new PageImpl<>(content, pageable, banners.getTotalElements());
    }

    /**
     * 배너 상세 정보를 조회합니다.
     */
    public BannerDtlResponseDto getBannerDetail(Long bannerId) {
        log.info("배너 상세 조회 시작 - bannerId: {}", bannerId);
        
        BannerEntity banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> {
                    log.error("배너를 찾을 수 없음 - bannerId: {}", bannerId);
                    return new IllegalArgumentException("배너를 찾을 수 없습니다: " + bannerId);
                });

        BannerDtlResponseDto result = convertToDto(banner);
        log.info("배너 상세 조회 완료 - bannerId: {}", bannerId);
        return result;
    }

    /**
     * 배너 엔티티를 DTO로 변환합니다.
     */
    private BannerDtlResponseDto convertToDto(BannerEntity entity) {
        BannerDtlResponseDto dto = new BannerDtlResponseDto();
        dto.setBannerId(entity.getBannerId());
        dto.setBannerNm(entity.getBannerNm());
        dto.setBannerUrl(entity.getBannerUrl());
        dto.setActiveYn(entity.getActiveYn());
        dto.setBannerStart(entity.getBannerStart());
        dto.setBannerEnd(entity.getBannerEnd());
        dto.setFullExpYn(entity.getFullExpYn());
        if (entity.getBannerType() != null) {
            dto.setBannerTypeId(entity.getBannerType().getBannerTypeId());
            dto.setBannerTypeNm(entity.getBannerType().getBannerTypeNm());
        }

        if (entity.getBannerImg() != null) {
            dto.setImageId(entity.getBannerImg().getImageId());
            dto.setImgPath(entity.getBannerImg().getImgPath());
            dto.setImgFullPath(imageBaseUrl + "/" + entity.getBannerImg().getImgPath());
        }

        return dto;
    }

    /**
     * 배너 ID로 배너를 조회합니다.
     * @throws IllegalArgumentException 배너가 존재하지 않는 경우
     */
    private BannerEntity getBanner(Long bannerId) {
        return bannerRepository.findById(bannerId)
                .orElseThrow(() -> {
                    log.error("배너를 찾을 수 없음 - bannerId: {}", bannerId);
                    return new IllegalArgumentException("Banner not found with ID: " + bannerId);
                });
    }

    /**
     * 배너 타입 ID로 배너 타입을 조회합니다.
     * @throws IllegalArgumentException 배너 타입이 존재하지 않는 경우
     */
    private BannerTypeEntity getBannerType(Long bannerTypeId) {
        return bannerTypeRepository.findById(bannerTypeId)
                .orElseThrow(() -> {
                    log.error("배너 타입을 찾을 수 없음 - bannerTypeId: {}", bannerTypeId);
                    return new IllegalArgumentException("BannerType not found with ID: " + bannerTypeId);
                });
    }
}