package com.cnco.campusflow.code;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCodeEntity is a Querydsl query type for CodeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCodeEntity extends EntityPathBase<CodeEntity> {

    private static final long serialVersionUID = 903209818L;

    public static final QCodeEntity codeEntity = new QCodeEntity("codeEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final StringPath activeYn = createString("activeYn");

    public final StringPath codeCat = createString("codeCat");

    public final StringPath codeCd = createString("codeCd");

    public final NumberPath<Long> codeId = createNumber("codeId", Long.class);

    public final StringPath codeNm = createString("codeNm");

    public final NumberPath<Integer> codeOrder = createNumber("codeOrder", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QCodeEntity(String variable) {
        super(CodeEntity.class, forVariable(variable));
    }

    public QCodeEntity(Path<? extends CodeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCodeEntity(PathMetadata metadata) {
        super(CodeEntity.class, metadata);
    }

}

