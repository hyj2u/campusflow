package com.cnco.campusflow.community;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReplyEntity is a Querydsl query type for ReplyEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReplyEntity extends EntityPathBase<ReplyEntity> {

    private static final long serialVersionUID = -918563215L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReplyEntity replyEntity = new QReplyEntity("replyEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final com.cnco.campusflow.user.QAppUserEntity appUser;

    public final StringPath blindYn = createString("blindYn");

    public final QCommunityBoardEntity board;

    public final StringPath content = createString("content");

    public final StringPath deleteYn = createString("deleteYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final NumberPath<Integer> likeCnt = createNumber("likeCnt", Integer.class);

    public final NumberPath<Long> replyId = createNumber("replyId", Long.class);

    public final StringPath secretYn = createString("secretYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public final NumberPath<Long> upTreeId = createNumber("upTreeId", Long.class);

    public QReplyEntity(String variable) {
        this(ReplyEntity.class, forVariable(variable), INITS);
    }

    public QReplyEntity(Path<? extends ReplyEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReplyEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReplyEntity(PathMetadata metadata, PathInits inits) {
        this(ReplyEntity.class, metadata, inits);
    }

    public QReplyEntity(Class<? extends ReplyEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appUser = inits.isInitialized("appUser") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("appUser"), inits.get("appUser")) : null;
        this.board = inits.isInitialized("board") ? new QCommunityBoardEntity(forProperty("board"), inits.get("board")) : null;
    }

}

