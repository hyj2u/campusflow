package com.cnco.campusflow.push;

import com.cnco.campusflow.brand.QBrandEntity;
import com.cnco.campusflow.category.CategoryBasicResponseDto;
import com.cnco.campusflow.category.QCategoryEntity;
import com.cnco.campusflow.product.*;
import com.cnco.campusflow.store.QStoreEntity;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.user.QAppUserEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository

public class CustomAppUserPushRepositoryImpl implements CustomAppUserPushRepository {
    private final JPAQueryFactory queryFactory;
    private QAppUserPushEntity push = QAppUserPushEntity.appUserPushEntity;


    public Page<AppUserPushEntity> getPushList(AppUserEntity appUser, String sendType, Pageable pageable) {


        BooleanBuilder builder = new BooleanBuilder()
                .and(push.appUser.eq(appUser))
                .and(push.activeYn.eq("Y"))
                .and(push.showYn.eq("Y"));


        if (sendType != null && !sendType.isBlank()) {
            builder.and(push.sendType.codeCd.eq(sendType));
        }

        List<AppUserPushEntity> results = queryFactory
                .selectFrom(push)
                .where(builder)
                .orderBy(push.sendDttm.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(push.count())
                .from(push)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

}
