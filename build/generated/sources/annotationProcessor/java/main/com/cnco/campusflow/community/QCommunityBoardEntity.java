package com.cnco.campusflow.community;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunityBoardEntity is a Querydsl query type for CommunityBoardEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityBoardEntity extends EntityPathBase<CommunityBoardEntity> {

    private static final long serialVersionUID = -973410244L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunityBoardEntity communityBoardEntity = new QCommunityBoardEntity("communityBoardEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final com.cnco.campusflow.user.QAppUserEntity appUser;

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final com.cnco.campusflow.code.QCodeEntity boardType;

    public final StringPath content = createString("content");

    public final ListPath<com.cnco.campusflow.image.ImageEntity, com.cnco.campusflow.image.QImageEntity> images = this.<com.cnco.campusflow.image.ImageEntity, com.cnco.campusflow.image.QImageEntity>createList("images", com.cnco.campusflow.image.ImageEntity.class, com.cnco.campusflow.image.QImageEntity.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Integer> likeCnt = createNumber("likeCnt", Integer.class);

    public final StringPath secretYn = createString("secretYn");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public final NumberPath<Integer> viewCnt = createNumber("viewCnt", Integer.class);

    public QCommunityBoardEntity(String variable) {
        this(CommunityBoardEntity.class, forVariable(variable), INITS);
    }

    public QCommunityBoardEntity(Path<? extends CommunityBoardEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunityBoardEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunityBoardEntity(PathMetadata metadata, PathInits inits) {
        this(CommunityBoardEntity.class, metadata, inits);
    }

    public QCommunityBoardEntity(Class<? extends CommunityBoardEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appUser = inits.isInitialized("appUser") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("appUser"), inits.get("appUser")) : null;
        this.boardType = inits.isInitialized("boardType") ? new com.cnco.campusflow.code.QCodeEntity(forProperty("boardType")) : null;
    }

}

