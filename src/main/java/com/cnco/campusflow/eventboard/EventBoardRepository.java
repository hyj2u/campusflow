package com.cnco.campusflow.eventboard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventBoardRepository extends JpaRepository<EventBoardEntity, Long> {
    Page<EventBoardEntity> findAllByOrderByInsertTimestampDesc(Pageable pageable);
} 