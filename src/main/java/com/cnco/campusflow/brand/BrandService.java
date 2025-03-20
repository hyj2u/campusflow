package com.cnco.campusflow.brand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandService {
    private final BrandRepository brandRepository;

    public List<BrandEntity> getBrands() {
        return brandRepository.findAllByOrderByOrderNum();
    }

    /**
     * 브랜드 추가
     *
     * @param brandEntity 브랜드 요청 DTO
     * @return 저장된 BrandEntity
     */

    public BrandEntity addBrand(BrandEntity brandEntity) {
        // 브랜드 중복 확인 (예: 브랜드명 기준)
        if (brandEntity.getBrandId() == null && brandRepository.existsByBrandNm(brandEntity.getBrandNm())) {
            throw new IllegalArgumentException("Brand name already exists.");
        }
        BrandEntity saveBrand;

        // BrandEntity 생성 또는 기존 엔티티 수정
        if (brandEntity.getBrandId() == null) {
            // 새 BrandEntity 생성
            saveBrand = new BrandEntity();
        } else {
            // 기존 BrandEntity 수정
            saveBrand = brandRepository.findById(brandEntity.getBrandId())
                    .orElseThrow(() -> new IllegalArgumentException("Brand not found with ID: " + brandEntity.getBrandId()));
        }

        // BrandEntity 필드 설정
        saveBrand.setBrandNm(brandEntity.getBrandNm());
        saveBrand.setOrderNum(brandEntity.getOrderNum());

        // 저장
        return brandRepository.save(saveBrand);
    }
    public void deleteBrand(Long brandId) {
       brandRepository.deleteById(brandId);
    }
}
