package com.cnco.campusflow.option;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOptionEntity is a Querydsl query type for OptionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOptionEntity extends EntityPathBase<OptionEntity> {

    private static final long serialVersionUID = 1529754010L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOptionEntity optionEntity = new QOptionEntity("optionEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final com.cnco.campusflow.code.QCodeEntity code;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final com.cnco.campusflow.optgrp.QOptGrpEntity optGrp;

    public final QOptionHqEntity optionHq;

    public final NumberPath<Long> optionId = createNumber("optionId", Long.class);

    public final StringPath optionNm = createString("optionNm");

    public final ListPath<com.cnco.campusflow.optdtl.OptDtlEntity, com.cnco.campusflow.optdtl.QOptDtlEntity> options = this.<com.cnco.campusflow.optdtl.OptDtlEntity, com.cnco.campusflow.optdtl.QOptDtlEntity>createList("options", com.cnco.campusflow.optdtl.OptDtlEntity.class, com.cnco.campusflow.optdtl.QOptDtlEntity.class, PathInits.DIRECT2);

    public final StringPath requireYn = createString("requireYn");

    public final com.cnco.campusflow.store.QStoreEntity store;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QOptionEntity(String variable) {
        this(OptionEntity.class, forVariable(variable), INITS);
    }

    public QOptionEntity(Path<? extends OptionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOptionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOptionEntity(PathMetadata metadata, PathInits inits) {
        this(OptionEntity.class, metadata, inits);
    }

    public QOptionEntity(Class<? extends OptionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.code = inits.isInitialized("code") ? new com.cnco.campusflow.code.QCodeEntity(forProperty("code")) : null;
        this.optGrp = inits.isInitialized("optGrp") ? new com.cnco.campusflow.optgrp.QOptGrpEntity(forProperty("optGrp"), inits.get("optGrp")) : null;
        this.optionHq = inits.isInitialized("optionHq") ? new QOptionHqEntity(forProperty("optionHq"), inits.get("optionHq")) : null;
        this.store = inits.isInitialized("store") ? new com.cnco.campusflow.store.QStoreEntity(forProperty("store"), inits.get("store")) : null;
    }

}

