package com.cnco.campusflow.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConsumerEntity is a Querydsl query type for ConsumerEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConsumerEntity extends EntityPathBase<ConsumerEntity> {

    private static final long serialVersionUID = -1518571664L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConsumerEntity consumerEntity = new QConsumerEntity("consumerEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final com.cnco.campusflow.user.QAppUserEntity appUser;

    public final NumberPath<Long> consumerId = createNumber("consumerId", Long.class);

    public final StringPath deliveryDemand = createString("deliveryDemand");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final QOrderAddressEntity orderAddress;

    public final StringPath storeDemand = createString("storeDemand");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QConsumerEntity(String variable) {
        this(ConsumerEntity.class, forVariable(variable), INITS);
    }

    public QConsumerEntity(Path<? extends ConsumerEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConsumerEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConsumerEntity(PathMetadata metadata, PathInits inits) {
        this(ConsumerEntity.class, metadata, inits);
    }

    public QConsumerEntity(Class<? extends ConsumerEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appUser = inits.isInitialized("appUser") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("appUser"), inits.get("appUser")) : null;
        this.orderAddress = inits.isInitialized("orderAddress") ? new QOrderAddressEntity(forProperty("orderAddress"), inits.get("orderAddress")) : null;
    }

}

