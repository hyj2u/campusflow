package com.cnco.campusflow.option;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionHqRepository extends JpaRepository<OptionHqEntity, Long> , CustomOptionHqRepository{
    List<OptionHqEntity> findByOptGrpOptGrpHqId(Long optGrpHqId); // 옵션 그룹 ID로 조회
    Page<OptionHqEntity> findByOptGrpOptGrpHqIdAndOptionHqNmLike(Long optGrpHqId, String optionHqNm, Pageable pageable);

    boolean existsByOptGrpOptGrpHqId(Long optGrpHqId); // 옵션 그룹 존재 여부 확인


}
