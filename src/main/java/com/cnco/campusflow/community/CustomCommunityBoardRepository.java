package com.cnco.campusflow.community;


import com.cnco.campusflow.user.AppUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomCommunityBoardRepository  {
    Page<CommunityBoardEntity> findFreeBoardWithSortingAndSearch(Integer collegeId,String order,String search, Pageable pageable);
    Page<CommunityBoardEntity> getQnaBoardWithSortingAndSearch(Integer collegeId, String order, String search, Pageable pageable);
    Page<CommunityBoardEntity> findBoardsByMyReplies(AppUserEntity user, String codeCd, Pageable pageable);
    Page<CommunityBoardEntity> findBoardsByMyLikes(AppUserEntity user, Pageable pageable);
}
