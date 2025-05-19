package com.cnco.campusflow.news;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNewsReportEntity is a Querydsl query type for NewsReportEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNewsReportEntity extends EntityPathBase<NewsReportEntity> {

    private static final long serialVersionUID = 1962162222L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNewsReportEntity newsReportEntity = new QNewsReportEntity("newsReportEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final NumberPath<Long> appUserId = createNumber("appUserId", Long.class);

    public final StringPath comment = createString("comment");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final QNewsEntity news;

    public final NumberPath<Long> reportId = createNumber("reportId", Long.class);

    public final StringPath reportReason = createString("reportReason");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public final NumberPath<Integer> viewCnt = createNumber("viewCnt", Integer.class);

    public QNewsReportEntity(String variable) {
        this(NewsReportEntity.class, forVariable(variable), INITS);
    }

    public QNewsReportEntity(Path<? extends NewsReportEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNewsReportEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNewsReportEntity(PathMetadata metadata, PathInits inits) {
        this(NewsReportEntity.class, metadata, inits);
    }

    public QNewsReportEntity(Class<? extends NewsReportEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.news = inits.isInitialized("news") ? new QNewsEntity(forProperty("news")) : null;
    }

}

