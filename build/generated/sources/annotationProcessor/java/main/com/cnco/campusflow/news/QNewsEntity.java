package com.cnco.campusflow.news;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNewsEntity is a Querydsl query type for NewsEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNewsEntity extends EntityPathBase<NewsEntity> {

    private static final long serialVersionUID = -1378667750L;

    public static final QNewsEntity newsEntity = new QNewsEntity("newsEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final StringPath activeYn = createString("activeYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Long> newsId = createNumber("newsId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public final StringPath url = createString("url");

    public final NumberPath<Integer> viewCnt = createNumber("viewCnt", Integer.class);

    public QNewsEntity(String variable) {
        super(NewsEntity.class, forVariable(variable));
    }

    public QNewsEntity(Path<? extends NewsEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNewsEntity(PathMetadata metadata) {
        super(NewsEntity.class, metadata);
    }

}

