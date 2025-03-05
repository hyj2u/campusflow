
package com.cnco.campusflow.community;

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
    public Page<CommunityBoardEntity> findFreeBoardWithSorting(String order, Pageable pageable) {

        // 기본 정렬: 최신순 (insertTimestamp 기준)
        var query = queryFactory
                .selectFrom(QCommunityBoardEntity.communityBoardEntity)
                .where(communityBoardEntity.boardType.codeCd.eq("free"));

        if ("reply".equalsIgnoreCase(order)) {
            // 댓글 개수 기준 정렬 (LEFT JOIN + GROUP BY 활용)
            query.leftJoin(replyEntity).on(replyEntity.board.eq(communityBoardEntity))
                    .groupBy(communityBoardEntity)
                    .orderBy(replyEntity.count().desc()); // 댓글 개수 많은 순 정렬
        } else {
            query.orderBy(communityBoardEntity.insertTimestamp.desc()); // 최신순 정렬
        }

        // 페이징 적용
        List<CommunityBoardEntity> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 조회
        long total = queryFactory
                .select(communityBoardEntity.count())
                .from(communityBoardEntity)
                .where(communityBoardEntity.boardType.codeCd.eq("free"))
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }
}

