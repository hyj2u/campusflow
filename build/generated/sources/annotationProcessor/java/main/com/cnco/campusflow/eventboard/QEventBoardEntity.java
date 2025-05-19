package com.cnco.campusflow.eventboard;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEventBoardEntity is a Querydsl query type for EventBoardEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventBoardEntity extends EntityPathBase<EventBoardEntity> {

    private static final long serialVersionUID = 81846170L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEventBoardEntity eventBoardEntity = new QEventBoardEntity("eventBoardEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final com.cnco.campusflow.user.QAppUserEntity appUser;

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final com.cnco.campusflow.code.QCodeEntity boardType;

    public final NumberPath<Integer> brandId = createNumber("brandId", Integer.class);

    public final StringPath content = createString("content");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final StringPath fullExpYn = createString("fullExpYn");

    public final ListPath<com.cnco.campusflow.image.ImageEntity, com.cnco.campusflow.image.QImageEntity> images = this.<com.cnco.campusflow.image.ImageEntity, com.cnco.campusflow.image.QImageEntity>createList("images", com.cnco.campusflow.image.ImageEntity.class, com.cnco.campusflow.image.QImageEntity.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final StringPath pushYn = createString("pushYn");

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final ListPath<EventBoardMappEntity, QEventBoardMappEntity> storeMappings = this.<EventBoardMappEntity, QEventBoardMappEntity>createList("storeMappings", EventBoardMappEntity.class, QEventBoardMappEntity.class, PathInits.DIRECT2);

    public final StringPath talkYn = createString("talkYn");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public final NumberPath<Integer> viewCnt = createNumber("viewCnt", Integer.class);

    public QEventBoardEntity(String variable) {
        this(EventBoardEntity.class, forVariable(variable), INITS);
    }

    public QEventBoardEntity(Path<? extends EventBoardEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEventBoardEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEventBoardEntity(PathMetadata metadata, PathInits inits) {
        this(EventBoardEntity.class, metadata, inits);
    }

    public QEventBoardEntity(Class<? extends EventBoardEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appUser = inits.isInitialized("appUser") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("appUser"), inits.get("appUser")) : null;
        this.boardType = inits.isInitialized("boardType") ? new com.cnco.campusflow.code.QCodeEntity(forProperty("boardType")) : null;
    }

}

