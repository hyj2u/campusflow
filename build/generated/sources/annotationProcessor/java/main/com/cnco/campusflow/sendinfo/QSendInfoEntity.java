package com.cnco.campusflow.sendinfo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSendInfoEntity is a Querydsl query type for SendInfoEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSendInfoEntity extends EntityPathBase<SendInfoEntity> {

    private static final long serialVersionUID = -1734121446L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSendInfoEntity sendInfoEntity = new QSendInfoEntity("sendInfoEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final StringPath phone = createString("phone");

    public final com.cnco.campusflow.user.QAppUserEntity receiver;

    public final DateTimePath<java.time.LocalDateTime> reqSendTm = createDateTime("reqSendTm", java.time.LocalDateTime.class);

    public final NumberPath<Long> sendId = createNumber("sendId", Long.class);

    public final StringPath sendMsg = createString("sendMsg");

    public final StringPath sendStatus = createString("sendStatus");

    public final StringPath sendTitle = createString("sendTitle");

    public final StringPath sendType = createString("sendType");

    public final DateTimePath<java.time.LocalDateTime> sentTm = createDateTime("sentTm", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QSendInfoEntity(String variable) {
        this(SendInfoEntity.class, forVariable(variable), INITS);
    }

    public QSendInfoEntity(Path<? extends SendInfoEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSendInfoEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSendInfoEntity(PathMetadata metadata, PathInits inits) {
        this(SendInfoEntity.class, metadata, inits);
    }

    public QSendInfoEntity(Class<? extends SendInfoEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiver = inits.isInitialized("receiver") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("receiver"), inits.get("receiver")) : null;
    }

}

