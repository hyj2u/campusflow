package com.cnco.campusflow.jwt;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRefreshTokenEntity is a Querydsl query type for RefreshTokenEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRefreshTokenEntity extends EntityPathBase<RefreshTokenEntity> {

    private static final long serialVersionUID = 299331967L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRefreshTokenEntity refreshTokenEntity = new QRefreshTokenEntity("refreshTokenEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final com.cnco.campusflow.user.QAppUserEntity appUser;

    public final StringPath deviceInfo = createString("deviceInfo");

    public final DateTimePath<java.util.Date> expiryDate = createDateTime("expiryDate", java.util.Date.class);

    public final StringPath fcmToken = createString("fcmToken");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final StringPath token = createString("token");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QRefreshTokenEntity(String variable) {
        this(RefreshTokenEntity.class, forVariable(variable), INITS);
    }

    public QRefreshTokenEntity(Path<? extends RefreshTokenEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRefreshTokenEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRefreshTokenEntity(PathMetadata metadata, PathInits inits) {
        this(RefreshTokenEntity.class, metadata, inits);
    }

    public QRefreshTokenEntity(Class<? extends RefreshTokenEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appUser = inits.isInitialized("appUser") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("appUser"), inits.get("appUser")) : null;
    }

}

