package com.cnco.campusflow.community;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunityLikeEntity is a Querydsl query type for CommunityLikeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityLikeEntity extends EntityPathBase<CommunityLikeEntity> {

    private static final long serialVersionUID = 311637543L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunityLikeEntity communityLikeEntity = new QCommunityLikeEntity("communityLikeEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final com.cnco.campusflow.user.QAppUserEntity appUser;

    public final QCommunityBoardEntity board;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Long> likeId = createNumber("likeId", Long.class);

    public final QReplyEntity reply;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QCommunityLikeEntity(String variable) {
        this(CommunityLikeEntity.class, forVariable(variable), INITS);
    }

    public QCommunityLikeEntity(Path<? extends CommunityLikeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunityLikeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunityLikeEntity(PathMetadata metadata, PathInits inits) {
        this(CommunityLikeEntity.class, metadata, inits);
    }

    public QCommunityLikeEntity(Class<? extends CommunityLikeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appUser = inits.isInitialized("appUser") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("appUser"), inits.get("appUser")) : null;
        this.board = inits.isInitialized("board") ? new QCommunityBoardEntity(forProperty("board"), inits.get("board")) : null;
        this.reply = inits.isInitialized("reply") ? new QReplyEntity(forProperty("reply"), inits.get("reply")) : null;
    }

}

