package com.cnco.campusflow.optgrp;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOptGrpHqEntity is a Querydsl query type for OptGrpHqEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOptGrpHqEntity extends EntityPathBase<OptGrpHqEntity> {

    private static final long serialVersionUID = 970191747L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOptGrpHqEntity optGrpHqEntity = new QOptGrpHqEntity("optGrpHqEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final com.cnco.campusflow.brand.QBrandEntity brand;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Long> optGrpHqId = createNumber("optGrpHqId", Long.class);

    public final StringPath optGrpHqNm = createString("optGrpHqNm");

    public final StringPath orderNum = createString("orderNum");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QOptGrpHqEntity(String variable) {
        this(OptGrpHqEntity.class, forVariable(variable), INITS);
    }

    public QOptGrpHqEntity(Path<? extends OptGrpHqEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOptGrpHqEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOptGrpHqEntity(PathMetadata metadata, PathInits inits) {
        this(OptGrpHqEntity.class, metadata, inits);
    }

    public QOptGrpHqEntity(Class<? extends OptGrpHqEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.brand = inits.isInitialized("brand") ? new com.cnco.campusflow.brand.QBrandEntity(forProperty("brand")) : null;
    }

}

