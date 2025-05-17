package com.cnco.campusflow.gifticon;

import com.cnco.campusflow.user.AppUserEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.cnco.campusflow.gifticon.QAppUserGifticonEntity.appUserGifticonEntity;

@Repository
@RequiredArgsConstructor
public class CustomGifticonRepositoryImpl implements CustomGifticonRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<GifticonEntity> findGifticonList(AppUserEntity appUser, String type, String activeYn, Pageable pageable) {
        List<AppUserGifticonEntity> content = queryFactory
                .selectFrom(appUserGifticonEntity)
                .where(
                    appUserGifticonEntity.receiver.userId.eq(appUser.getUserId()),
                    typeEq(type),
                    activeYnEq(activeYn)
                )
                .orderBy(appUserGifticonEntity.gifticon.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(appUserGifticonEntity.count())
                .from(appUserGifticonEntity)
                .where(
                    appUserGifticonEntity.receiver.userId.eq(appUser.getUserId()),
                    typeEq(type),
                    activeYnEq(activeYn)
                )
                .fetchOne();

        return new PageImpl<>(
            content.stream().map(AppUserGifticonEntity::getGifticon).toList(),
            pageable,
            total
        );
    }

    private BooleanExpression typeEq(String type) {
        return StringUtils.hasText(type) ? appUserGifticonEntity.type.eq(type) : null;
    }

    private BooleanExpression activeYnEq(String activeYn) {
        return StringUtils.hasText(activeYn) ? appUserGifticonEntity.activeYn.eq(activeYn) : null;
    }
} 