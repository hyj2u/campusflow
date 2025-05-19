package com.cnco.campusflow.store;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStoreEntity is a Querydsl query type for StoreEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStoreEntity extends EntityPathBase<StoreEntity> {

    private static final long serialVersionUID = 1484093600L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStoreEntity storeEntity = new QStoreEntity("storeEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final StringPath addressDtl = createString("addressDtl");

    public final StringPath addressMain = createString("addressMain");

    public final StringPath approveYn = createString("approveYn");

    public final com.cnco.campusflow.brand.QBrandEntity brand;

    public final NumberPath<Double> couponRate = createNumber("couponRate", Double.class);

    public final TimePath<java.time.LocalTime> dayCloseTm = createTime("dayCloseTm", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> dayOpenTm = createTime("dayOpenTm", java.time.LocalTime.class);

    public final StringPath deliveryNote = createString("deliveryNote");

    public final StringPath deliveryYn = createString("deliveryYn");

    public final NumberPath<Double> discountRate = createNumber("discountRate", Double.class);

    public final StringPath email = createString("email");

    public final StringPath inhereYn = createString("inhereYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final StringPath introduction = createString("introduction");

    public final StringPath kcpCorp = createString("kcpCorp");

    public final StringPath kcpGen = createString("kcpGen");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final com.cnco.campusflow.image.QImageEntity mainImg;

    public final StringPath mainPhone = createString("mainPhone");

    public final StringPath managerPhone = createString("managerPhone");

    public final StringPath mobilePhone = createString("mobilePhone");

    public final StringPath openYn = createString("openYn");

    public final StringPath orderReservYn = createString("orderReservYn");

    public final StringPath owner = createString("owner");

    public final NumberPath<Double> pointRate = createNumber("pointRate", Double.class);

    public final NumberPath<Long> qrImg = createNumber("qrImg", Long.class);

    public final TimePath<java.time.LocalTime> satCloseTm = createTime("satCloseTm", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> satOpenTm = createTime("satOpenTm", java.time.LocalTime.class);

    public final StringPath secretMoneyYn = createString("secretMoneyYn");

    public final NumberPath<Integer> stampNum = createNumber("stampNum", Integer.class);

    public final StringPath stampYn = createString("stampYn");

    public final StringPath storeCd = createString("storeCd");

    public final NumberPath<Long> storeId = createNumber("storeId", Long.class);

    public final StringPath storeNm = createString("storeNm");

    public final StringPath storeStatus = createString("storeStatus");

    public final TimePath<java.time.LocalTime> sunCloseTm = createTime("sunCloseTm", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> sunOpenTm = createTime("sunOpenTm", java.time.LocalTime.class);

    public final StringPath togoYn = createString("togoYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QStoreEntity(String variable) {
        this(StoreEntity.class, forVariable(variable), INITS);
    }

    public QStoreEntity(Path<? extends StoreEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStoreEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStoreEntity(PathMetadata metadata, PathInits inits) {
        this(StoreEntity.class, metadata, inits);
    }

    public QStoreEntity(Class<? extends StoreEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.brand = inits.isInitialized("brand") ? new com.cnco.campusflow.brand.QBrandEntity(forProperty("brand")) : null;
        this.mainImg = inits.isInitialized("mainImg") ? new com.cnco.campusflow.image.QImageEntity(forProperty("mainImg"), inits.get("mainImg")) : null;
    }

}

