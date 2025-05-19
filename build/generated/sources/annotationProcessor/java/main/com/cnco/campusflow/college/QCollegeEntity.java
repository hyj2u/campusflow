package com.cnco.campusflow.college;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCollegeEntity is a Querydsl query type for CollegeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCollegeEntity extends EntityPathBase<CollegeEntity> {

    private static final long serialVersionUID = -1061786164L;

    public static final QCollegeEntity collegeEntity = new QCollegeEntity("collegeEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final StringPath collegeCode = createString("collegeCode");

    public final NumberPath<Integer> collegeId = createNumber("collegeId", Integer.class);

    public final StringPath collegeName = createString("collegeName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QCollegeEntity(String variable) {
        super(CollegeEntity.class, forVariable(variable));
    }

    public QCollegeEntity(Path<? extends CollegeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCollegeEntity(PathMetadata metadata) {
        super(CollegeEntity.class, metadata);
    }

}

