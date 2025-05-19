package com.cnco.campusflow.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductEntity is a Querydsl query type for ProductEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductEntity extends EntityPathBase<ProductEntity> {

    private static final long serialVersionUID = 912304252L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductEntity productEntity = new QProductEntity("productEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final StringPath activeYn = createString("activeYn");

    public final com.cnco.campusflow.image.QImageEntity allergyImg;

    public final StringPath allergyInfo = createString("allergyInfo");

    public final StringPath appVisibleYn = createString("appVisibleYn");

    public final NumberPath<Integer> buyCnt = createNumber("buyCnt", Integer.class);

    public final SetPath<com.cnco.campusflow.category.CategoryEntity, com.cnco.campusflow.category.QCategoryEntity> categories = this.<com.cnco.campusflow.category.CategoryEntity, com.cnco.campusflow.category.QCategoryEntity>createSet("categories", com.cnco.campusflow.category.CategoryEntity.class, com.cnco.campusflow.category.QCategoryEntity.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final StringPath kioskVisibleYn = createString("kioskVisibleYn");

    public final com.cnco.campusflow.image.QImageEntity mainImg;

    public final SetPath<com.cnco.campusflow.optgrp.OptGrpEntity, com.cnco.campusflow.optgrp.QOptGrpEntity> optGrp = this.<com.cnco.campusflow.optgrp.OptGrpEntity, com.cnco.campusflow.optgrp.QOptGrpEntity>createSet("optGrp", com.cnco.campusflow.optgrp.OptGrpEntity.class, com.cnco.campusflow.optgrp.QOptGrpEntity.class, PathInits.DIRECT2);

    public final SetPath<com.cnco.campusflow.option.OptionEntity, com.cnco.campusflow.option.QOptionEntity> options = this.<com.cnco.campusflow.option.OptionEntity, com.cnco.campusflow.option.QOptionEntity>createSet("options", com.cnco.campusflow.option.OptionEntity.class, com.cnco.campusflow.option.QOptionEntity.class, PathInits.DIRECT2);

    public final com.cnco.campusflow.image.QImageEntity originImg;

    public final StringPath originInfo = createString("originInfo");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath productDtl = createString("productDtl");

    public final StringPath productHqEngNm = createString("productHqEngNm");

    public final QProductHqEntity productHqEntity;

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final StringPath productNm = createString("productNm");

    public final ListPath<ProductTagEntity, QProductTagEntity> productTags = this.<ProductTagEntity, QProductTagEntity>createList("productTags", ProductTagEntity.class, QProductTagEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> stampUseCnt = createNumber("stampUseCnt", Integer.class);

    public final StringPath stampUseYn = createString("stampUseYn");

    public final com.cnco.campusflow.store.QStoreEntity store;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QProductEntity(String variable) {
        this(ProductEntity.class, forVariable(variable), INITS);
    }

    public QProductEntity(Path<? extends ProductEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductEntity(PathMetadata metadata, PathInits inits) {
        this(ProductEntity.class, metadata, inits);
    }

    public QProductEntity(Class<? extends ProductEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.allergyImg = inits.isInitialized("allergyImg") ? new com.cnco.campusflow.image.QImageEntity(forProperty("allergyImg"), inits.get("allergyImg")) : null;
        this.mainImg = inits.isInitialized("mainImg") ? new com.cnco.campusflow.image.QImageEntity(forProperty("mainImg"), inits.get("mainImg")) : null;
        this.originImg = inits.isInitialized("originImg") ? new com.cnco.campusflow.image.QImageEntity(forProperty("originImg"), inits.get("originImg")) : null;
        this.productHqEntity = inits.isInitialized("productHqEntity") ? new QProductHqEntity(forProperty("productHqEntity"), inits.get("productHqEntity")) : null;
        this.store = inits.isInitialized("store") ? new com.cnco.campusflow.store.QStoreEntity(forProperty("store"), inits.get("store")) : null;
    }

}

