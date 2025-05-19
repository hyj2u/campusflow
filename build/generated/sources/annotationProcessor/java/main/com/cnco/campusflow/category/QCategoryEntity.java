package com.cnco.campusflow.category;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategoryEntity is a Querydsl query type for CategoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryEntity extends EntityPathBase<CategoryEntity> {

    private static final long serialVersionUID = -551375622L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategoryEntity categoryEntity = new QCategoryEntity("categoryEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final com.cnco.campusflow.brand.QBrandEntity brand;

    public final QCategoryHqEntity categoryHqEntity;

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    public final StringPath categoryNm = createString("categoryNm");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Integer> orderNum = createNumber("orderNum", Integer.class);

    public final com.cnco.campusflow.store.QStoreEntity store;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QCategoryEntity(String variable) {
        this(CategoryEntity.class, forVariable(variable), INITS);
    }

    public QCategoryEntity(Path<? extends CategoryEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategoryEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategoryEntity(PathMetadata metadata, PathInits inits) {
        this(CategoryEntity.class, metadata, inits);
    }

    public QCategoryEntity(Class<? extends CategoryEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.brand = inits.isInitialized("brand") ? new com.cnco.campusflow.brand.QBrandEntity(forProperty("brand")) : null;
        this.categoryHqEntity = inits.isInitialized("categoryHqEntity") ? new QCategoryHqEntity(forProperty("categoryHqEntity"), inits.get("categoryHqEntity")) : null;
        this.store = inits.isInitialized("store") ? new com.cnco.campusflow.store.QStoreEntity(forProperty("store"), inits.get("store")) : null;
    }

}

