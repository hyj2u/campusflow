package com.cnco.campusflow.terms;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTermsEntity is a Querydsl query type for TermsEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTermsEntity extends EntityPathBase<TermsEntity> {

    private static final long serialVersionUID = -7291796L;

    public static final QTermsEntity termsEntity = new QTermsEntity("termsEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final StringPath service = createString("service");

    public final StringPath termsContent = createString("termsContent");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QTermsEntity(String variable) {
        super(TermsEntity.class, forVariable(variable));
    }

    public QTermsEntity(Path<? extends TermsEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTermsEntity(PathMetadata metadata) {
        super(TermsEntity.class, metadata);
    }

}

