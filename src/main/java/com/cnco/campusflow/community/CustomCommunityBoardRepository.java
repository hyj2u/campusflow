package com.cnco.campusflow.community;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomCommunityBoardRepository  {
    Page<CommunityBoardEntity> findFreeBoardWithSorting(String order, Pageable pageable);
    Page<CommunityBoardEntity> findQnABoardWithSorting(Integer collegeId, String order, Pageable pageable);

}
