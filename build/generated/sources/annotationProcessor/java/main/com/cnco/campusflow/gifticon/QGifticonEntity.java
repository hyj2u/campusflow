package com.cnco.campusflow.gifticon;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGifticonEntity is a Querydsl query type for GifticonEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGifticonEntity extends EntityPathBase<GifticonEntity> {

    private static final long serialVersionUID = -1601866406L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGifticonEntity gifticonEntity = new QGifticonEntity("gifticonEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final StringPath activeYn = createString("activeYn");

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> gifticonId = createNumber("gifticonId", Long.class);

    public final StringPath gifticonName = createString("gifticonName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final com.cnco.campusflow.product.QProductEntity product;

    public final StringPath sendType = createString("sendType");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QGifticonEntity(String variable) {
        this(GifticonEntity.class, forVariable(variable), INITS);
    }

    public QGifticonEntity(Path<? extends GifticonEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGifticonEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGifticonEntity(PathMetadata metadata, PathInits inits) {
        this(GifticonEntity.class, metadata, inits);
    }

    public QGifticonEntity(Class<? extends GifticonEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.cnco.campusflow.product.QProductEntity(forProperty("product"), inits.get("product")) : null;
    }

}

