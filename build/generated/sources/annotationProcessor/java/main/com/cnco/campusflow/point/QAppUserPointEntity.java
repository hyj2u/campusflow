package com.cnco.campusflow.point;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAppUserPointEntity is a Querydsl query type for AppUserPointEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAppUserPointEntity extends EntityPathBase<AppUserPointEntity> {

    private static final long serialVersionUID = -972710052L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAppUserPointEntity appUserPointEntity = new QAppUserPointEntity("appUserPointEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final com.cnco.campusflow.user.QAppUserEntity appUser;

    public final NumberPath<Long> appUserPointId = createNumber("appUserPointId", Long.class);

    public final NumberPath<Integer> currentPointCount = createNumber("currentPointCount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> endTimestamp = createDateTime("endTimestamp", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final StringPath note = createString("note");

    public final com.cnco.campusflow.store.QStoreEntity store;

    public final NumberPath<Integer> totalPointCount = createNumber("totalPointCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QAppUserPointEntity(String variable) {
        this(AppUserPointEntity.class, forVariable(variable), INITS);
    }

    public QAppUserPointEntity(Path<? extends AppUserPointEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAppUserPointEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAppUserPointEntity(PathMetadata metadata, PathInits inits) {
        this(AppUserPointEntity.class, metadata, inits);
    }

    public QAppUserPointEntity(Class<? extends AppUserPointEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appUser = inits.isInitialized("appUser") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("appUser"), inits.get("appUser")) : null;
        this.store = inits.isInitialized("store") ? new com.cnco.campusflow.store.QStoreEntity(forProperty("store"), inits.get("store")) : null;
    }

}

