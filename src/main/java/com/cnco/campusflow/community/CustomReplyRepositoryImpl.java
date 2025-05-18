package com.cnco.campusflow.community;

import com.cnco.campusflow.user.AppUserEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class CustomReplyRepositoryImpl implements CustomReplyRepository {

    private final JPAQueryFactory queryFactory;
    private QReplyEntity reply = QReplyEntity.replyEntity;
    private QCommunityLikeEntity like = QCommunityLikeEntity.communityLikeEntity;


    public Page<ReplyEntity> getRepliesWithMyLikes(AppUserEntity appUser, Pageable pageable) {


        BooleanBuilder builder = new BooleanBuilder()
                .and(like.appUser.eq(appUser))
                .and(like.reply.isNotNull());

        List<ReplyEntity> results = queryFactory
                .select(like.reply)
                .from(like)
                .join(like.reply, reply)
                .where(builder)
                .orderBy(reply.insertTimestamp.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(reply.count())
                .from(like)
                .join(like.reply, reply)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }
}
