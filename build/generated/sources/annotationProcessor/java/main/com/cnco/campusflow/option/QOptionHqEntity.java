package com.cnco.campusflow.option;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOptionHqEntity is a Querydsl query type for OptionHqEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOptionHqEntity extends EntityPathBase<OptionHqEntity> {

    private static final long serialVersionUID = -825168317L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOptionHqEntity optionHqEntity = new QOptionHqEntity("optionHqEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final com.cnco.campusflow.code.QCodeEntity code;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final com.cnco.campusflow.optgrp.QOptGrpHqEntity optGrp;

    public final NumberPath<Long> optionHqId = createNumber("optionHqId", Long.class);

    public final StringPath optionHqNm = createString("optionHqNm");

    public final ListPath<com.cnco.campusflow.optdtl.OptDtlHqEntity, com.cnco.campusflow.optdtl.QOptDtlHqEntity> options = this.<com.cnco.campusflow.optdtl.OptDtlHqEntity, com.cnco.campusflow.optdtl.QOptDtlHqEntity>createList("options", com.cnco.campusflow.optdtl.OptDtlHqEntity.class, com.cnco.campusflow.optdtl.QOptDtlHqEntity.class, PathInits.DIRECT2);

    public final StringPath requireYn = createString("requireYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QOptionHqEntity(String variable) {
        this(OptionHqEntity.class, forVariable(variable), INITS);
    }

    public QOptionHqEntity(Path<? extends OptionHqEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOptionHqEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOptionHqEntity(PathMetadata metadata, PathInits inits) {
        this(OptionHqEntity.class, metadata, inits);
    }

    public QOptionHqEntity(Class<? extends OptionHqEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.code = inits.isInitialized("code") ? new com.cnco.campusflow.code.QCodeEntity(forProperty("code")) : null;
        this.optGrp = inits.isInitialized("optGrp") ? new com.cnco.campusflow.optgrp.QOptGrpHqEntity(forProperty("optGrp"), inits.get("optGrp")) : null;
    }

}

