package com.cnco.campusflow.brand;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBrandEntity is a Querydsl query type for BrandEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBrandEntity extends EntityPathBase<BrandEntity> {

    private static final long serialVersionUID = 505692268L;

    public static final QBrandEntity brandEntity = new QBrandEntity("brandEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final NumberPath<Long> brandId = createNumber("brandId", Long.class);

    public final StringPath brandNm = createString("brandNm");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Integer> orderNum = createNumber("orderNum", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QBrandEntity(String variable) {
        super(BrandEntity.class, forVariable(variable));
    }

    public QBrandEntity(Path<? extends BrandEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBrandEntity(PathMetadata metadata) {
        super(BrandEntity.class, metadata);
    }

}

