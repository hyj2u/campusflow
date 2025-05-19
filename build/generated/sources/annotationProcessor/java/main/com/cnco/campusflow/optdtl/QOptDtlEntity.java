package com.cnco.campusflow.optdtl;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOptDtlEntity is a Querydsl query type for OptDtlEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOptDtlEntity extends EntityPathBase<OptDtlEntity> {

    private static final long serialVersionUID = 546814714L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOptDtlEntity optDtlEntity = new QOptDtlEntity("optDtlEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final StringPath dtlUseYn = createString("dtlUseYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Integer> max = createNumber("max", Integer.class);

    public final NumberPath<Integer> min = createNumber("min", Integer.class);

    public final StringPath opDtlNm = createString("opDtlNm");

    public final QOptDtlHqEntity optDtlHqEntity;

    public final NumberPath<Long> optDtlId = createNumber("optDtlId", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final com.cnco.campusflow.store.QStoreEntity store;

    public final StringPath type = createString("type");

    public final NumberPath<Integer> unitPrice = createNumber("unitPrice", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QOptDtlEntity(String variable) {
        this(OptDtlEntity.class, forVariable(variable), INITS);
    }

    public QOptDtlEntity(Path<? extends OptDtlEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOptDtlEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOptDtlEntity(PathMetadata metadata, PathInits inits) {
        this(OptDtlEntity.class, metadata, inits);
    }

    public QOptDtlEntity(Class<? extends OptDtlEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.optDtlHqEntity = inits.isInitialized("optDtlHqEntity") ? new QOptDtlHqEntity(forProperty("optDtlHqEntity")) : null;
        this.store = inits.isInitialized("store") ? new com.cnco.campusflow.store.QStoreEntity(forProperty("store"), inits.get("store")) : null;
    }

}

