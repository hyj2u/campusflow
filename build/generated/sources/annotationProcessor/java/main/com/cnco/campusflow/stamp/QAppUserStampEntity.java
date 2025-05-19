package com.cnco.campusflow.stamp;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAppUserStampEntity is a Querydsl query type for AppUserStampEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAppUserStampEntity extends EntityPathBase<AppUserStampEntity> {

    private static final long serialVersionUID = -197926340L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAppUserStampEntity appUserStampEntity = new QAppUserStampEntity("appUserStampEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final com.cnco.campusflow.user.QAppUserEntity appUser;

    public final NumberPath<Long> appUserStampId = createNumber("appUserStampId", Long.class);

    public final NumberPath<Integer> currentStampCount = createNumber("currentStampCount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> endTimestamp = createDateTime("endTimestamp", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final StringPath note = createString("note");

    public final com.cnco.campusflow.store.QStoreEntity store;

    public final NumberPath<Integer> totalStampCount = createNumber("totalStampCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QAppUserStampEntity(String variable) {
        this(AppUserStampEntity.class, forVariable(variable), INITS);
    }

    public QAppUserStampEntity(Path<? extends AppUserStampEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAppUserStampEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAppUserStampEntity(PathMetadata metadata, PathInits inits) {
        this(AppUserStampEntity.class, metadata, inits);
    }

    public QAppUserStampEntity(Class<? extends AppUserStampEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appUser = inits.isInitialized("appUser") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("appUser"), inits.get("appUser")) : null;
        this.store = inits.isInitialized("store") ? new com.cnco.campusflow.store.QStoreEntity(forProperty("store"), inits.get("store")) : null;
    }

}

