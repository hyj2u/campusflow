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
public class CustomCommunityBoardRepositoryImpl implements CustomCommunityBoardRepository {

    private final JPAQueryFactory queryFactory;

    private QCommunityBoardEntity communityBoardEntity = QCommunityBoardEntity.communityBoardEntity;
    private QReplyEntity replyEntity = QReplyEntity.replyEntity;
    QCommunityLikeEntity like = QCommunityLikeEntity.communityLikeEntity;



    @Override
    public Page<CommunityBoardEntity> findFreeBoardWithSortingAndSearch(
            Integer collegeId,
            String order,
            String search,
            Pageable pageable
    ) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(communityBoardEntity.boardType.codeCd.eq("free"));

        if (collegeId != null) {
            builder.and(communityBoardEntity.appUser.college.collegeId.eq(collegeId));
        }

        if (search != null && !search.trim().isEmpty()) {
            builder.and(
                    communityBoardEntity.title.containsIgnoreCase(search)
                            .or(communityBoardEntity.content.containsIgnoreCase(search))
            );
        }

        var query = queryFactory
                .selectFrom(communityBoardEntity)
                .where(builder);

        if ("popular".equalsIgnoreCase(order)) {
            query.orderBy(communityBoardEntity.likeCnt.desc());
        } else {
            query.orderBy(communityBoardEntity.insertTimestamp.desc());
        }

        List<CommunityBoardEntity> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(communityBoardEntity.count())
                .from(communityBoardEntity)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<CommunityBoardEntity> getQnaBoardWithSortingAndSearch(Integer collegeId, String order, String search, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(communityBoardEntity.boardType.codeCd.eq("qna"));

        if (collegeId != null) {
            builder.and(communityBoardEntity.appUser.college.collegeId.eq(collegeId));
        }

        if (search != null && !search.trim().isEmpty()) {
            builder.and(
                    communityBoardEntity.title.containsIgnoreCase(search)
                            .or(communityBoardEntity.content.containsIgnoreCase(search))
            );
        }

        var query = queryFactory
                .selectFrom(communityBoardEntity)
                .where(builder);

        if ("popular".equalsIgnoreCase(order)) {
            query.orderBy(communityBoardEntity.likeCnt.desc());
        } else {
            query.orderBy(communityBoardEntity.insertTimestamp.desc());
        }

        List<CommunityBoardEntity> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(communityBoardEntity.count())
                .from(communityBoardEntity)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<CommunityBoardEntity> findBoardsByMyReplies(AppUserEntity user, String codeCd, Pageable pageable) {
        QCommunityBoardEntity board = QCommunityBoardEntity.communityBoardEntity;
        QReplyEntity reply = QReplyEntity.replyEntity;

        BooleanBuilder builder = new BooleanBuilder()
                .and(reply.appUser.eq(user));

        if (codeCd != null && !codeCd.trim().isEmpty()) {
            builder.and(board.boardType.codeCd.eq(codeCd));  // ✅ 여기가 핵심
        }

        List<CommunityBoardEntity> results = queryFactory
                .selectDistinct(board)
                .from(reply)
                .join(reply.board, board)
                .where(builder)
                .orderBy(board.insertTimestamp.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(board.countDistinct())
                .from(reply)
                .join(reply.board, board)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }
    @Override
    public Page<CommunityBoardEntity> findBoardsByMyLikes(AppUserEntity user, Pageable pageable) {
        QCommunityBoardEntity board = QCommunityBoardEntity.communityBoardEntity;
        QCommunityLikeEntity like = QCommunityLikeEntity.communityLikeEntity;

        BooleanBuilder builder = new BooleanBuilder()
                .and(like.appUser.eq(user))
                .and(like.board.isNotNull())
                .and(like.reply.isNull());  // 댓글 좋아요 제외

        List<CommunityBoardEntity> results = queryFactory
                .selectDistinct(like.board)
                .from(like)
                .join(like.board, board)
                .where(builder)
                .orderBy(board.insertTimestamp.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(like.board.countDistinct())
                .from(like)
                .join(like.board, board)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }
}
