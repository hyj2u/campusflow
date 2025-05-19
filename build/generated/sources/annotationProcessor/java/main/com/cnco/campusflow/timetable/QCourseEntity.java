package com.cnco.campusflow.timetable;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCourseEntity is a Querydsl query type for CourseEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCourseEntity extends EntityPathBase<CourseEntity> {

    private static final long serialVersionUID = -1675669726L;

    public static final QCourseEntity courseEntity = new QCourseEntity("courseEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final StringPath color = createString("color");

    public final NumberPath<Long> courseId = createNumber("courseId", Long.class);

    public final StringPath courseName = createString("courseName");

    public final ListPath<com.cnco.campusflow.code.CodeEntity, com.cnco.campusflow.code.QCodeEntity> days = this.<com.cnco.campusflow.code.CodeEntity, com.cnco.campusflow.code.QCodeEntity>createList("days", com.cnco.campusflow.code.CodeEntity.class, com.cnco.campusflow.code.QCodeEntity.class, PathInits.DIRECT2);

    public final StringPath endTime = createString("endTime");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final StringPath location = createString("location");

    public final StringPath professor = createString("professor");

    public final StringPath startTime = createString("startTime");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QCourseEntity(String variable) {
        super(CourseEntity.class, forVariable(variable));
    }

    public QCourseEntity(Path<? extends CourseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCourseEntity(PathMetadata metadata) {
        super(CourseEntity.class, metadata);
    }

}

