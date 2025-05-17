package com.cnco.campusflow.community;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
    List<ReplyEntity> findAllByBoardBoardIdOrderByInsertTimestampAsc(Long boardId);
    List<ReplyEntity> findAllByBoardBoardIdOrderByInsertTimestampDesc(Long boardId);
    void deleteAllByBoardBoardId(Long boardId);
}
