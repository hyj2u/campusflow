package com.cnco.campusflow.menu;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMenuOptionEntity is a Querydsl query type for MenuOptionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenuOptionEntity extends EntityPathBase<MenuOptionEntity> {

    private static final long serialVersionUID = -227889617L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMenuOptionEntity menuOptionEntity = new QMenuOptionEntity("menuOptionEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final NumberPath<Integer> chosenNum = createNumber("chosenNum", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Long> menuOptId = createNumber("menuOptId", Long.class);

    public final com.cnco.campusflow.optdtl.QOptDtlEntity optDtlEntity;

    public final com.cnco.campusflow.option.QOptionEntity optionEntity;

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QMenuOptionEntity(String variable) {
        this(MenuOptionEntity.class, forVariable(variable), INITS);
    }

    public QMenuOptionEntity(Path<? extends MenuOptionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMenuOptionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMenuOptionEntity(PathMetadata metadata, PathInits inits) {
        this(MenuOptionEntity.class, metadata, inits);
    }

    public QMenuOptionEntity(Class<? extends MenuOptionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.optDtlEntity = inits.isInitialized("optDtlEntity") ? new com.cnco.campusflow.optdtl.QOptDtlEntity(forProperty("optDtlEntity"), inits.get("optDtlEntity")) : null;
        this.optionEntity = inits.isInitialized("optionEntity") ? new com.cnco.campusflow.option.QOptionEntity(forProperty("optionEntity"), inits.get("optionEntity")) : null;
    }

}

