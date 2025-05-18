package com.cnco.campusflow.community;

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
}
