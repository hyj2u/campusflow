package com.cnco.campusflow.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoardEntity, Long> {
    @Query("SELECT n FROM NoticeBoardEntity n WHERE n.boardType.codeCd = :codeCd ORDER BY n.insertTimestamp DESC")
    Page<NoticeBoardEntity> findAllByBoardTypeCodeCdOrderByInsertTimestampDesc(@Param("codeCd") String codeCd, Pageable pageable);
} 