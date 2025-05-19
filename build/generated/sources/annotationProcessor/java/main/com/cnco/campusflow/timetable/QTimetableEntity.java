package com.cnco.campusflow.timetable;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTimetableEntity is a Querydsl query type for TimetableEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTimetableEntity extends EntityPathBase<TimetableEntity> {

    private static final long serialVersionUID = 1547140768L;

    public static final QTimetableEntity timetableEntity = new QTimetableEntity("timetableEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final StringPath category = createString("category");

    public final ListPath<CourseEntity, QCourseEntity> courses = this.<CourseEntity, QCourseEntity>createList("courses", CourseEntity.class, QCourseEntity.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final NumberPath<Long> tmTableId = createNumber("tmTableId", Long.class);

    public final StringPath tmTableName = createString("tmTableName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public QTimetableEntity(String variable) {
        super(TimetableEntity.class, forVariable(variable));
    }

    public QTimetableEntity(Path<? extends TimetableEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTimetableEntity(PathMetadata metadata) {
        super(TimetableEntity.class, metadata);
    }

}

