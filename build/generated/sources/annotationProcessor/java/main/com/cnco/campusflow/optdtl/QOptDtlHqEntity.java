package com.cnco.campusflow.optdtl;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOptDtlHqEntity is a Querydsl query type for OptDtlHqEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOptDtlHqEntity extends EntityPathBase<OptDtlHqEntity> {

    private static final long serialVersionUID = -537026653L;

    public static final QOptDtlHqEntity optDtlHqEntity = new QOptDtlHqEntity("optDtlHqEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Integer> max = createNumber("max", Integer.class);

    public final NumberPath<Integer> min = createNumber("min", Integer.class);

    public final StringPath opDtlHqNm = createString("opDtlHqNm");

    public final NumberPath<Long> optDtlHqId = createNumber("optDtlHqId", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath type = createString("type");

    public final NumberPath<Integer> unitPrice = createNumber("unitPrice", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QOptDtlHqEntity(String variable) {
        super(OptDtlHqEntity.class, forVariable(variable));
    }

    public QOptDtlHqEntity(Path<? extends OptDtlHqEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOptDtlHqEntity(PathMetadata metadata) {
        super(OptDtlHqEntity.class, metadata);
    }

}

