package com.cnco.campusflow.menu;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMenuEntity is a Querydsl query type for MenuEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenuEntity extends EntityPathBase<MenuEntity> {

    private static final long serialVersionUID = -816297318L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMenuEntity menuEntity = new QMenuEntity("menuEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Long> menuId = createNumber("menuId", Long.class);

    public final ListPath<MenuOptionEntity, QMenuOptionEntity> options = this.<MenuOptionEntity, QMenuOptionEntity>createList("options", MenuOptionEntity.class, QMenuOptionEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> orderCnt = createNumber("orderCnt", Integer.class);

    public final com.cnco.campusflow.product.QProductEntity product;

    public final com.cnco.campusflow.store.QStoreEntity store;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QMenuEntity(String variable) {
        this(MenuEntity.class, forVariable(variable), INITS);
    }

    public QMenuEntity(Path<? extends MenuEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMenuEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMenuEntity(PathMetadata metadata, PathInits inits) {
        this(MenuEntity.class, metadata, inits);
    }

    public QMenuEntity(Class<? extends MenuEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.cnco.campusflow.product.QProductEntity(forProperty("product"), inits.get("product")) : null;
        this.store = inits.isInitialized("store") ? new com.cnco.campusflow.store.QStoreEntity(forProperty("store"), inits.get("store")) : null;
    }

}

