package com.cnco.campusflow.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductTagHqEntity is a Querydsl query type for ProductTagHqEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductTagHqEntity extends EntityPathBase<ProductTagHqEntity> {

    private static final long serialVersionUID = -1134407923L;

    public static final QProductTagHqEntity productTagHqEntity = new QProductTagHqEntity("productTagHqEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Long> productTagHqId = createNumber("productTagHqId", Long.class);

    public final StringPath productTagHqNm = createString("productTagHqNm");

    public final StringPath showYn = createString("showYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QProductTagHqEntity(String variable) {
        super(ProductTagHqEntity.class, forVariable(variable));
    }

    public QProductTagHqEntity(Path<? extends ProductTagHqEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductTagHqEntity(PathMetadata metadata) {
        super(ProductTagHqEntity.class, metadata);
    }

}

