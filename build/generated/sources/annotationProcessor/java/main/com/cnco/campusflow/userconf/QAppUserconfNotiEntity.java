package com.cnco.campusflow.userconf;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAppUserconfNotiEntity is a Querydsl query type for AppUserconfNotiEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAppUserconfNotiEntity extends EntityPathBase<AppUserconfNotiEntity> {

    private static final long serialVersionUID = 1037705057L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAppUserconfNotiEntity appUserconfNotiEntity = new QAppUserconfNotiEntity("appUserconfNotiEntity");

    public final StringPath answerNotiYn = createString("answerNotiYn");

    public final com.cnco.campusflow.user.QAppUserEntity appUser;

    public final NumberPath<Long> appUserconfNotiId = createNumber("appUserconfNotiId", Long.class);

    public final StringPath boardStopNotiYn = createString("boardStopNotiYn");

    public final TimePath<java.time.LocalTime> doNotDisturbEndTime = createTime("doNotDisturbEndTime", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> doNotDisturbStartTime = createTime("doNotDisturbStartTime", java.time.LocalTime.class);

    public final StringPath doNotDisturbYn = createString("doNotDisturbYn");

    public final StringPath lectureNotiYn = createString("lectureNotiYn");

    public final DateTimePath<java.time.LocalDateTime> marketingAgreeTimestamp = createDateTime("marketingAgreeTimestamp", java.time.LocalDateTime.class);

    public final StringPath marketingYn = createString("marketingYn");

    public final StringPath membershipNotiYn = createString("membershipNotiYn");

    public final StringPath newsNotiYn = createString("newsNotiYn");

    public final StringPath orderNotiYn = createString("orderNotiYn");

    public final StringPath replyNotiYn = createString("replyNotiYn");

    public QAppUserconfNotiEntity(String variable) {
        this(AppUserconfNotiEntity.class, forVariable(variable), INITS);
    }

    public QAppUserconfNotiEntity(Path<? extends AppUserconfNotiEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAppUserconfNotiEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAppUserconfNotiEntity(PathMetadata metadata, PathInits inits) {
        this(AppUserconfNotiEntity.class, metadata, inits);
    }

    public QAppUserconfNotiEntity(Class<? extends AppUserconfNotiEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appUser = inits.isInitialized("appUser") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("appUser"), inits.get("appUser")) : null;
    }

}

