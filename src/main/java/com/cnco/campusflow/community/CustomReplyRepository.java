package com.cnco.campusflow.community;


import com.cnco.campusflow.user.AppUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomReplyRepository {
    Page<ReplyEntity> getRepliesWithMyLikes(AppUserEntity user, Pageable pageable);
}
