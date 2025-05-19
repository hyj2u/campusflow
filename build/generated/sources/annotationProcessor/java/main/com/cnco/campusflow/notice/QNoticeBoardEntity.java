package com.cnco.campusflow.notice;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoticeBoardEntity is a Querydsl query type for NoticeBoardEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoticeBoardEntity extends EntityPathBase<NoticeBoardEntity> {

    private static final long serialVersionUID = 1308862578L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoticeBoardEntity noticeBoardEntity = new QNoticeBoardEntity("noticeBoardEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final com.cnco.campusflow.user.QAppUserEntity appUser;

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final com.cnco.campusflow.code.QCodeEntity boardType;

    public final StringPath content = createString("content");

    public final ListPath<com.cnco.campusflow.image.ImageEntity, com.cnco.campusflow.image.QImageEntity> images = this.<com.cnco.campusflow.image.ImageEntity, com.cnco.campusflow.image.QImageEntity>createList("images", com.cnco.campusflow.image.ImageEntity.class, com.cnco.campusflow.image.QImageEntity.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final StringPath pushYn = createString("pushYn");

    public final ListPath<NoticeBoardMappEntity, QNoticeBoardMappEntity> storeMappings = this.<NoticeBoardMappEntity, QNoticeBoardMappEntity>createList("storeMappings", NoticeBoardMappEntity.class, QNoticeBoardMappEntity.class, PathInits.DIRECT2);

    public final StringPath talkYn = createString("talkYn");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public final NumberPath<Integer> viewCnt = createNumber("viewCnt", Integer.class);

    public QNoticeBoardEntity(String variable) {
        this(NoticeBoardEntity.class, forVariable(variable), INITS);
    }

    public QNoticeBoardEntity(Path<? extends NoticeBoardEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoticeBoardEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoticeBoardEntity(PathMetadata metadata, PathInits inits) {
        this(NoticeBoardEntity.class, metadata, inits);
    }

    public QNoticeBoardEntity(Class<? extends NoticeBoardEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appUser = inits.isInitialized("appUser") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("appUser"), inits.get("appUser")) : null;
        this.boardType = inits.isInitialized("boardType") ? new com.cnco.campusflow.code.QCodeEntity(forProperty("boardType")) : null;
    }

}

