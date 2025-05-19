package com.cnco.campusflow.menu;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavoriteMenuEntity is a Querydsl query type for FavoriteMenuEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavoriteMenuEntity extends EntityPathBase<FavoriteMenuEntity> {

    private static final long serialVersionUID = 1991091542L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavoriteMenuEntity favoriteMenuEntity = new QFavoriteMenuEntity("favoriteMenuEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final NumberPath<Long> favMenuId = createNumber("favMenuId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final QMenuEntity menu;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public final com.cnco.campusflow.user.QAppUserEntity user;

    public QFavoriteMenuEntity(String variable) {
        this(FavoriteMenuEntity.class, forVariable(variable), INITS);
    }

    public QFavoriteMenuEntity(Path<? extends FavoriteMenuEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavoriteMenuEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavoriteMenuEntity(PathMetadata metadata, PathInits inits) {
        this(FavoriteMenuEntity.class, metadata, inits);
    }

    public QFavoriteMenuEntity(Class<? extends FavoriteMenuEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.menu = inits.isInitialized("menu") ? new QMenuEntity(forProperty("menu"), inits.get("menu")) : null;
        this.user = inits.isInitialized("user") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("user"), inits.get("user")) : null;
    }

}

