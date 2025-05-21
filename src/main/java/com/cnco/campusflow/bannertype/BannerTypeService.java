package com.cnco.campusflow.bannertype;

import com.cnco.campusflow.code.CodeEntity;
import com.cnco.campusflow.code.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BannerTypeService {

    private final BannerTypeRepository bannerTypeRepository;
    private final CodeRepository codeRepository;

    /**
     * 활성화된 배너 타입의 코드 목록을 조회합니다.
     * ID와 이름만 포함된 간소화된 형태로 반환합니다.
     * @return 활성화된 배너 타입의 코드 목록
     */
    public List<BannerTypeResponseDto> getBannerTypeCodes() {
        log.info("배너 타입 코드 목록 조회 시작");
        
        List<BannerTypeResponseDto> result = bannerTypeRepository.findByActiveYn('Y')
                .stream()
                .map(this::convertToCodeDto)
                .collect(Collectors.toList());

        log.info("배너 타입 코드 목록 조회 완료 - 총 {}건", result.size());
        return result;
    }

    /**
     * 배너 타입 엔티티를 코드성 DTO로 변환합니다.
     * ID와 이름만 설정하고 나머지 필드는 null로 둡니다.
     */
    private BannerTypeResponseDto convertToCodeDto(BannerTypeEntity entity) {
        BannerTypeResponseDto dto = new BannerTypeResponseDto();
        dto.setBannerTypeId(entity.getBannerTypeId());
        dto.setBannerTypeCd(entity.getBannerTypeCd());
        dto.setBannerTypeNm(entity.getBannerTypeNm());
        return dto;
    }
}