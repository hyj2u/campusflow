package com.cnco.campusflow.notice;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoticeBoardMappEntity is a Querydsl query type for NoticeBoardMappEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoticeBoardMappEntity extends EntityPathBase<NoticeBoardMappEntity> {

    private static final long serialVersionUID = 2000059558L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoticeBoardMappEntity noticeBoardMappEntity = new QNoticeBoardMappEntity("noticeBoardMappEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Long> mappId = createNumber("mappId", Long.class);

    public final QNoticeBoardEntity noticeBoard;

    public final NumberPath<Long> storeId = createNumber("storeId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QNoticeBoardMappEntity(String variable) {
        this(NoticeBoardMappEntity.class, forVariable(variable), INITS);
    }

    public QNoticeBoardMappEntity(Path<? extends NoticeBoardMappEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoticeBoardMappEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoticeBoardMappEntity(PathMetadata metadata, PathInits inits) {
        this(NoticeBoardMappEntity.class, metadata, inits);
    }

    public QNoticeBoardMappEntity(Class<? extends NoticeBoardMappEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.noticeBoard = inits.isInitialized("noticeBoard") ? new QNoticeBoardEntity(forProperty("noticeBoard"), inits.get("noticeBoard")) : null;
    }

}

