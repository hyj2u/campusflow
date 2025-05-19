package com.cnco.campusflow.store;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavoriteStoreEntity is a Querydsl query type for FavoriteStoreEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavoriteStoreEntity extends EntityPathBase<FavoriteStoreEntity> {

    private static final long serialVersionUID = -679460508L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavoriteStoreEntity favoriteStoreEntity = new QFavoriteStoreEntity("favoriteStoreEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final NumberPath<Long> fav_store_id = createNumber("fav_store_id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final QStoreEntity store;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public final com.cnco.campusflow.user.QAppUserEntity user;

    public QFavoriteStoreEntity(String variable) {
        this(FavoriteStoreEntity.class, forVariable(variable), INITS);
    }

    public QFavoriteStoreEntity(Path<? extends FavoriteStoreEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavoriteStoreEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavoriteStoreEntity(PathMetadata metadata, PathInits inits) {
        this(FavoriteStoreEntity.class, metadata, inits);
    }

    public QFavoriteStoreEntity(Class<? extends FavoriteStoreEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new QStoreEntity(forProperty("store"), inits.get("store")) : null;
        this.user = inits.isInitialized("user") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("user"), inits.get("user")) : null;
    }

}

