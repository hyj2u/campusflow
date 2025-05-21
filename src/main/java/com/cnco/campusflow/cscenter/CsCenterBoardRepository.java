package com.cnco.campusflow.cscenter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CsCenterBoardRepository extends JpaRepository<CsCenterBoardEntity, Long> {
    @Query("SELECT c FROM CsCenterBoardEntity c WHERE c.deleteYn = 'N' OR c.deleteYn IS NULL ORDER BY c.insertTimestamp DESC")
    Page<CsCenterBoardEntity> findAllOrderByInsertTimestampDesc(Pageable pageable);

    @Query("SELECT c FROM CsCenterBoardEntity c WHERE c.appUser.appUserId = :appUserId AND (c.deleteYn = 'N' OR c.deleteYn IS NULL) ORDER BY c.insertTimestamp DESC")
    Page<CsCenterBoardEntity> findByAppUserIdOrderByInsertTimestampDesc(@Param("appUserId") Long appUserId, Pageable pageable);

    @Query("SELECT c FROM CsCenterBoardEntity c WHERE c.appUser.appUserId = :appUserId AND (c.deleteYn = 'N' OR c.deleteYn IS NULL)")
    Page<CsCenterBoardEntity> findByAppUser_AppUserId(@Param("appUserId") Long appUserId, Pageable pageable);
} 