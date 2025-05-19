package com.cnco.campusflow.eventboard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEventBoardMappEntity is a Querydsl query type for EventBoardMappEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventBoardMappEntity extends EntityPathBase<EventBoardMappEntity> {

    private static final long serialVersionUID = -2133598258L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEventBoardMappEntity eventBoardMappEntity = new QEventBoardMappEntity("eventBoardMappEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final QEventBoardEntity eventBoard;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Long> mappId = createNumber("mappId", Long.class);

    public final NumberPath<Long> storeId = createNumber("storeId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QEventBoardMappEntity(String variable) {
        this(EventBoardMappEntity.class, forVariable(variable), INITS);
    }

    public QEventBoardMappEntity(Path<? extends EventBoardMappEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEventBoardMappEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEventBoardMappEntity(PathMetadata metadata, PathInits inits) {
        this(EventBoardMappEntity.class, metadata, inits);
    }

    public QEventBoardMappEntity(Class<? extends EventBoardMappEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.eventBoard = inits.isInitialized("eventBoard") ? new QEventBoardEntity(forProperty("eventBoard"), inits.get("eventBoard")) : null;
    }

}

