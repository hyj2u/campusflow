package com.cnco.campusflow.push;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPushEntity is a Querydsl query type for PushEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPushEntity extends EntityPathBase<PushEntity> {

    private static final long serialVersionUID = -1205272710L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPushEntity pushEntity = new QPushEntity("pushEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final ComparablePath<Character> activeYn = createComparable("activeYn", Character.class);

    public final StringPath channel = createString("channel");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final StringPath link = createString("link");

    public final NumberPath<Long> pushId = createNumber("pushId", Long.class);

    public final com.cnco.campusflow.image.QImageEntity pushImg;

    public final DateTimePath<java.time.LocalDateTime> scheduledAt = createDateTime("scheduledAt", java.time.LocalDateTime.class);

    public final StringPath sendStatus = createString("sendStatus");

    public final StringPath sendType = createString("sendType");

    public final ComparablePath<Character> targetAllYn = createComparable("targetAllYn", Character.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QPushEntity(String variable) {
        this(PushEntity.class, forVariable(variable), INITS);
    }

    public QPushEntity(Path<? extends PushEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPushEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPushEntity(PathMetadata metadata, PathInits inits) {
        this(PushEntity.class, metadata, inits);
    }

    public QPushEntity(Class<? extends PushEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pushImg = inits.isInitialized("pushImg") ? new com.cnco.campusflow.image.QImageEntity(forProperty("pushImg"), inits.get("pushImg")) : null;
    }

}

