package com.cnco.campusflow.community;


import com.cnco.campusflow.user.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityLikeRepository extends JpaRepository<CommunityLikeEntity, Long> {

    void deleteAllByBoardAndAppUser(CommunityBoardEntity board, AppUserEntity appUser);
    void deleteAllByReplyAndAppUser(ReplyEntity reply, AppUserEntity appUser);
}
