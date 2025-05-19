package com.cnco.campusflow.optgrp;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOptGrpEntity is a Querydsl query type for OptGrpEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOptGrpEntity extends EntityPathBase<OptGrpEntity> {

    private static final long serialVersionUID = 1504806618L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOptGrpEntity optGrpEntity = new QOptGrpEntity("optGrpEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final com.cnco.campusflow.brand.QBrandEntity brand;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final QOptGrpHqEntity optGrpHqEntity;

    public final NumberPath<Long> optGrpId = createNumber("optGrpId", Long.class);

    public final StringPath optGrpNm = createString("optGrpNm");

    public final StringPath orderNum = createString("orderNum");

    public final com.cnco.campusflow.store.QStoreEntity store;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QOptGrpEntity(String variable) {
        this(OptGrpEntity.class, forVariable(variable), INITS);
    }

    public QOptGrpEntity(Path<? extends OptGrpEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOptGrpEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOptGrpEntity(PathMetadata metadata, PathInits inits) {
        this(OptGrpEntity.class, metadata, inits);
    }

    public QOptGrpEntity(Class<? extends OptGrpEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.brand = inits.isInitialized("brand") ? new com.cnco.campusflow.brand.QBrandEntity(forProperty("brand")) : null;
        this.optGrpHqEntity = inits.isInitialized("optGrpHqEntity") ? new QOptGrpHqEntity(forProperty("optGrpHqEntity"), inits.get("optGrpHqEntity")) : null;
        this.store = inits.isInitialized("store") ? new com.cnco.campusflow.store.QStoreEntity(forProperty("store"), inits.get("store")) : null;
    }

}

