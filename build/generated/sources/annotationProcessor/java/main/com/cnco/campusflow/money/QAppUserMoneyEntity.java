package com.cnco.campusflow.money;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAppUserMoneyEntity is a Querydsl query type for AppUserMoneyEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAppUserMoneyEntity extends EntityPathBase<AppUserMoneyEntity> {

    private static final long serialVersionUID = 262327644L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAppUserMoneyEntity appUserMoneyEntity = new QAppUserMoneyEntity("appUserMoneyEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final com.cnco.campusflow.user.QAppUserEntity appUser;

    public final NumberPath<Long> appUserMoneyId = createNumber("appUserMoneyId", Long.class);

    public final NumberPath<Integer> currentMoney = createNumber("currentMoney", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> endTimestamp = createDateTime("endTimestamp", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final StringPath note = createString("note");

    public final com.cnco.campusflow.store.QStoreEntity store;

    public final NumberPath<Integer> totalMoney = createNumber("totalMoney", Integer.class);

    public final StringPath type = createString("type");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QAppUserMoneyEntity(String variable) {
        this(AppUserMoneyEntity.class, forVariable(variable), INITS);
    }

    public QAppUserMoneyEntity(Path<? extends AppUserMoneyEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAppUserMoneyEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAppUserMoneyEntity(PathMetadata metadata, PathInits inits) {
        this(AppUserMoneyEntity.class, metadata, inits);
    }

    public QAppUserMoneyEntity(Class<? extends AppUserMoneyEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appUser = inits.isInitialized("appUser") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("appUser"), inits.get("appUser")) : null;
        this.store = inits.isInitialized("store") ? new com.cnco.campusflow.store.QStoreEntity(forProperty("store"), inits.get("store")) : null;
    }

}

