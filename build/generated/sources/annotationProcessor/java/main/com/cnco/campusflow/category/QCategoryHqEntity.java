package com.cnco.campusflow.category;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategoryHqEntity is a Querydsl query type for CategoryHqEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryHqEntity extends EntityPathBase<CategoryHqEntity> {

    private static final long serialVersionUID = 664015267L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategoryHqEntity categoryHqEntity = new QCategoryHqEntity("categoryHqEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final com.cnco.campusflow.brand.QBrandEntity brand;

    public final NumberPath<Long> categoryHqId = createNumber("categoryHqId", Long.class);

    public final StringPath categoryHqNm = createString("categoryHqNm");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Integer> orderNum = createNumber("orderNum", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QCategoryHqEntity(String variable) {
        this(CategoryHqEntity.class, forVariable(variable), INITS);
    }

    public QCategoryHqEntity(Path<? extends CategoryHqEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategoryHqEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategoryHqEntity(PathMetadata metadata, PathInits inits) {
        this(CategoryHqEntity.class, metadata, inits);
    }

    public QCategoryHqEntity(Class<? extends CategoryHqEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.brand = inits.isInitialized("brand") ? new com.cnco.campusflow.brand.QBrandEntity(forProperty("brand")) : null;
    }

}

