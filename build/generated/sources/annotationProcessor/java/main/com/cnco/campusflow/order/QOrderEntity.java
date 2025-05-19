package com.cnco.campusflow.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderEntity is a Querydsl query type for OrderEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderEntity extends EntityPathBase<OrderEntity> {

    private static final long serialVersionUID = -389278662L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderEntity orderEntity = new QOrderEntity("orderEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final QConsumerEntity consumer;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final ListPath<com.cnco.campusflow.menu.MenuEntity, com.cnco.campusflow.menu.QMenuEntity> menus = this.<com.cnco.campusflow.menu.MenuEntity, com.cnco.campusflow.menu.QMenuEntity>createList("menus", com.cnco.campusflow.menu.MenuEntity.class, com.cnco.campusflow.menu.QMenuEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath orderStatus = createString("orderStatus");

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QOrderEntity(String variable) {
        this(OrderEntity.class, forVariable(variable), INITS);
    }

    public QOrderEntity(Path<? extends OrderEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderEntity(PathMetadata metadata, PathInits inits) {
        this(OrderEntity.class, metadata, inits);
    }

    public QOrderEntity(Class<? extends OrderEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.consumer = inits.isInitialized("consumer") ? new QConsumerEntity(forProperty("consumer"), inits.get("consumer")) : null;
    }

}

