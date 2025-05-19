package com.cnco.campusflow.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductHqEntity is a Querydsl query type for ProductHqEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductHqEntity extends EntityPathBase<ProductHqEntity> {

    private static final long serialVersionUID = -1488898907L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductHqEntity productHqEntity = new QProductHqEntity("productHqEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final StringPath activeYn = createString("activeYn");

    public final com.cnco.campusflow.image.QImageEntity allergyImg;

    public final StringPath allergyInfo = createString("allergyInfo");

    public final StringPath appVisibleYn = createString("appVisibleYn");

    public final NumberPath<Integer> buyCnt = createNumber("buyCnt", Integer.class);

    public final SetPath<com.cnco.campusflow.category.CategoryHqEntity, com.cnco.campusflow.category.QCategoryHqEntity> categories = this.<com.cnco.campusflow.category.CategoryHqEntity, com.cnco.campusflow.category.QCategoryHqEntity>createSet("categories", com.cnco.campusflow.category.CategoryHqEntity.class, com.cnco.campusflow.category.QCategoryHqEntity.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final StringPath kioskVisibleYn = createString("kioskVisibleYn");

    public final com.cnco.campusflow.image.QImageEntity mainImg;

    public final SetPath<com.cnco.campusflow.optgrp.OptGrpHqEntity, com.cnco.campusflow.optgrp.QOptGrpHqEntity> optGrp = this.<com.cnco.campusflow.optgrp.OptGrpHqEntity, com.cnco.campusflow.optgrp.QOptGrpHqEntity>createSet("optGrp", com.cnco.campusflow.optgrp.OptGrpHqEntity.class, com.cnco.campusflow.optgrp.QOptGrpHqEntity.class, PathInits.DIRECT2);

    public final SetPath<com.cnco.campusflow.option.OptionHqEntity, com.cnco.campusflow.option.QOptionHqEntity> options = this.<com.cnco.campusflow.option.OptionHqEntity, com.cnco.campusflow.option.QOptionHqEntity>createSet("options", com.cnco.campusflow.option.OptionHqEntity.class, com.cnco.campusflow.option.QOptionHqEntity.class, PathInits.DIRECT2);

    public final com.cnco.campusflow.image.QImageEntity originImg;

    public final StringPath originInfo = createString("originInfo");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath productDtl = createString("productDtl");

    public final StringPath productHqEngNm = createString("productHqEngNm");

    public final NumberPath<Long> productHqId = createNumber("productHqId", Long.class);

    public final StringPath productHqNm = createString("productHqNm");

    public final ListPath<ProductTagHqEntity, QProductTagHqEntity> productTags = this.<ProductTagHqEntity, QProductTagHqEntity>createList("productTags", ProductTagHqEntity.class, QProductTagHqEntity.class, PathInits.DIRECT2);

    public final StringPath stampUseYn = createString("stampUseYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QProductHqEntity(String variable) {
        this(ProductHqEntity.class, forVariable(variable), INITS);
    }

    public QProductHqEntity(Path<? extends ProductHqEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductHqEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductHqEntity(PathMetadata metadata, PathInits inits) {
        this(ProductHqEntity.class, metadata, inits);
    }

    public QProductHqEntity(Class<? extends ProductHqEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.allergyImg = inits.isInitialized("allergyImg") ? new com.cnco.campusflow.image.QImageEntity(forProperty("allergyImg"), inits.get("allergyImg")) : null;
        this.mainImg = inits.isInitialized("mainImg") ? new com.cnco.campusflow.image.QImageEntity(forProperty("mainImg"), inits.get("mainImg")) : null;
        this.originImg = inits.isInitialized("originImg") ? new com.cnco.campusflow.image.QImageEntity(forProperty("originImg"), inits.get("originImg")) : null;
    }

}

