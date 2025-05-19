package com.cnco.campusflow.cscenter;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCsCenterReplyEntity is a Querydsl query type for CsCenterReplyEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCsCenterReplyEntity extends EntityPathBase<CsCenterReplyEntity> {

    private static final long serialVersionUID = 130325686L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCsCenterReplyEntity csCenterReplyEntity = new QCsCenterReplyEntity("csCenterReplyEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final com.cnco.campusflow.user.QAppUserEntity appUser;

    public final StringPath blindYn = createString("blindYn");

    public final QCsCenterBoardEntity board;

    public final StringPath content = createString("content");

    public final StringPath deleteYn = createString("deleteYn");

    public final StringPath helpfulYn = createString("helpfulYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final NumberPath<Integer> likeCnt = createNumber("likeCnt", Integer.class);

    public final NumberPath<Long> replyId = createNumber("replyId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public final NumberPath<Long> upTreeId = createNumber("upTreeId", Long.class);

    public QCsCenterReplyEntity(String variable) {
        this(CsCenterReplyEntity.class, forVariable(variable), INITS);
    }

    public QCsCenterReplyEntity(Path<? extends CsCenterReplyEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCsCenterReplyEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCsCenterReplyEntity(PathMetadata metadata, PathInits inits) {
        this(CsCenterReplyEntity.class, metadata, inits);
    }

    public QCsCenterReplyEntity(Class<? extends CsCenterReplyEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appUser = inits.isInitialized("appUser") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("appUser"), inits.get("appUser")) : null;
        this.board = inits.isInitialized("board") ? new QCsCenterBoardEntity(forProperty("board"), inits.get("board")) : null;
    }

}

