package com.cnco.campusflow.oftenqna;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOftenQnaEntity is a Querydsl query type for OftenQnaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOftenQnaEntity extends EntityPathBase<OftenQnaEntity> {

    private static final long serialVersionUID = -1302194982L;

    public static final QOftenQnaEntity oftenQnaEntity = new QOftenQnaEntity("oftenQnaEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public final NumberPath<Integer> viewCnt = createNumber("viewCnt", Integer.class);

    public QOftenQnaEntity(String variable) {
        super(OftenQnaEntity.class, forVariable(variable));
    }

    public QOftenQnaEntity(Path<? extends OftenQnaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOftenQnaEntity(PathMetadata metadata) {
        super(OftenQnaEntity.class, metadata);
    }

}

