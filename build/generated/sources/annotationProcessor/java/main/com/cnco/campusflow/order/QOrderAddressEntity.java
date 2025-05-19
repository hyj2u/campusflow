package com.cnco.campusflow.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderAddressEntity is a Querydsl query type for OrderAddressEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderAddressEntity extends EntityPathBase<OrderAddressEntity> {

    private static final long serialVersionUID = 1538045120L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderAddressEntity orderAddressEntity = new QOrderAddressEntity("orderAddressEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final StringPath addressDtl = createString("addressDtl");

    public final StringPath addressMain = createString("addressMain");

    public final com.cnco.campusflow.user.QAppUserEntity appUser;

    public final StringPath defaultYn = createString("defaultYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Long> orderAddrId = createNumber("orderAddrId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QOrderAddressEntity(String variable) {
        this(OrderAddressEntity.class, forVariable(variable), INITS);
    }

    public QOrderAddressEntity(Path<? extends OrderAddressEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderAddressEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderAddressEntity(PathMetadata metadata, PathInits inits) {
        this(OrderAddressEntity.class, metadata, inits);
    }

    public QOrderAddressEntity(Class<? extends OrderAddressEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appUser = inits.isInitialized("appUser") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("appUser"), inits.get("appUser")) : null;
    }

}

