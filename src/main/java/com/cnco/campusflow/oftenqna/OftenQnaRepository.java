package com.cnco.campusflow.oftenqna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OftenQnaRepository extends JpaRepository<OftenQnaEntity, Long> {
    @Query("SELECT o FROM OftenQnaEntity o ORDER BY o.insertTimestamp DESC")
    Page<OftenQnaEntity> findAllOrderByInsertTimestampDesc(Pageable pageable);

    @Query("SELECT o FROM OftenQnaEntity o WHERE o.category = :category ORDER BY o.insertTimestamp DESC")
    Page<OftenQnaEntity> findByCategoryOrderByInsertTimestampDesc(@Param("category") String category, Pageable pageable);
} 