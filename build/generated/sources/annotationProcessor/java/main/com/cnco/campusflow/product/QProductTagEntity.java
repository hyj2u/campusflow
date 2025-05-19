package com.cnco.campusflow.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductTagEntity is a Querydsl query type for ProductTagEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductTagEntity extends EntityPathBase<ProductTagEntity> {

    private static final long serialVersionUID = 1985297636L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductTagEntity productTagEntity = new QProductTagEntity("productTagEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final QProductTagHqEntity productTagHqEntity;

    public final NumberPath<Long> productTagId = createNumber("productTagId", Long.class);

    public final StringPath productTagNm = createString("productTagNm");

    public final StringPath showYn = createString("showYn");

    public final com.cnco.campusflow.store.QStoreEntity store;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QProductTagEntity(String variable) {
        this(ProductTagEntity.class, forVariable(variable), INITS);
    }

    public QProductTagEntity(Path<? extends ProductTagEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductTagEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductTagEntity(PathMetadata metadata, PathInits inits) {
        this(ProductTagEntity.class, metadata, inits);
    }

    public QProductTagEntity(Class<? extends ProductTagEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.productTagHqEntity = inits.isInitialized("productTagHqEntity") ? new QProductTagHqEntity(forProperty("productTagHqEntity")) : null;
        this.store = inits.isInitialized("store") ? new com.cnco.campusflow.store.QStoreEntity(forProperty("store"), inits.get("store")) : null;
    }

}

