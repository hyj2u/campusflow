package com.cnco.campusflow.gifticon;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAppUserGifticonEntity is a Querydsl query type for AppUserGifticonEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAppUserGifticonEntity extends EntityPathBase<AppUserGifticonEntity> {

    private static final long serialVersionUID = -1906110102L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAppUserGifticonEntity appUserGifticonEntity = new QAppUserGifticonEntity("appUserGifticonEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final StringPath activeYn = createString("activeYn");

    public final NumberPath<Long> appUserGifticonId = createNumber("appUserGifticonId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final QGifticonEntity gifticon;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final StringPath phone = createString("phone");

    public final com.cnco.campusflow.product.QProductEntity product;

    public final NumberPath<Long> purchaseAmount = createNumber("purchaseAmount", Long.class);

    public final com.cnco.campusflow.user.QAppUserEntity receiver;

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final com.cnco.campusflow.user.QAppUserEntity sender;

    public final com.cnco.campusflow.sendinfo.QSendInfoEntity sendInfo;

    public final StringPath type = createString("type");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public final StringPath useYn = createString("useYn");

    public QAppUserGifticonEntity(String variable) {
        this(AppUserGifticonEntity.class, forVariable(variable), INITS);
    }

    public QAppUserGifticonEntity(Path<? extends AppUserGifticonEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAppUserGifticonEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAppUserGifticonEntity(PathMetadata metadata, PathInits inits) {
        this(AppUserGifticonEntity.class, metadata, inits);
    }

    public QAppUserGifticonEntity(Class<? extends AppUserGifticonEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.gifticon = inits.isInitialized("gifticon") ? new QGifticonEntity(forProperty("gifticon"), inits.get("gifticon")) : null;
        this.product = inits.isInitialized("product") ? new com.cnco.campusflow.product.QProductEntity(forProperty("product"), inits.get("product")) : null;
        this.receiver = inits.isInitialized("receiver") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("receiver"), inits.get("receiver")) : null;
        this.sender = inits.isInitialized("sender") ? new com.cnco.campusflow.user.QAppUserEntity(forProperty("sender"), inits.get("sender")) : null;
        this.sendInfo = inits.isInitialized("sendInfo") ? new com.cnco.campusflow.sendinfo.QSendInfoEntity(forProperty("sendInfo"), inits.get("sendInfo")) : null;
    }

}

