package com.cnco.campusflow.coupon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 쿠폰 번호 관리를 위한 레포지토리 인터페이스
 */
@Repository
public interface CouponGenRepository extends JpaRepository<CouponGenEntity, Long> {
    
    /**
     * 쿠폰명으로 쿠폰 번호를 검색합니다.
     * 
     * @param keyword 검색 키워드
     * @param pageable 페이징 정보
     * @return 검색된 쿠폰 번호 목록
     */
    @Query("SELECT c FROM CouponGenEntity c WHERE c.couponName LIKE %:keyword%")
    Page<CouponGenEntity> findByCouponNameContaining(@Param("keyword") String keyword, Pageable pageable);
} 