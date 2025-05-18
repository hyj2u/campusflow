package com.cnco.campusflow.cscenter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CsCenterReplyRepository extends JpaRepository<CsCenterReplyEntity, Long> {
    List<CsCenterReplyEntity> findAllByBoardBoardIdOrderByInsertTimestampAsc(Long boardId);
    List<CsCenterReplyEntity> findAllByBoardBoardIdOrderByInsertTimestampDesc(Long boardId);
    void deleteAllByBoardBoardId(Long boardId);
} 