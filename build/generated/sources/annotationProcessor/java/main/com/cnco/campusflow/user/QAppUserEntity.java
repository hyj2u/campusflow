package com.cnco.campusflow.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAppUserEntity is a Querydsl query type for AppUserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAppUserEntity extends EntityPathBase<AppUserEntity> {

    private static final long serialVersionUID = -1566447997L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAppUserEntity appUserEntity = new QAppUserEntity("appUserEntity");

    public final com.cnco.campusflow.common.QBaseEntity _super = new com.cnco.campusflow.common.QBaseEntity(this);

    public final StringPath approveStatus = createString("approveStatus");

    public final NumberPath<Long> appUserId = createNumber("appUserId", Long.class);

    public final StringPath appUserName = createString("appUserName");

    public final DateTimePath<java.util.Date> birthday = createDateTime("birthday", java.util.Date.class);

    public final com.cnco.campusflow.college.QCollegeEntity college;

    public final StringPath collegeAdmissionYear = createString("collegeAdmissionYear");

    public final com.cnco.campusflow.image.QImageEntity collegeImg;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> insertTimestamp = _super.insertTimestamp;

    public final DateTimePath<java.time.LocalDateTime> lastLoginDt = createDateTime("lastLoginDt", java.time.LocalDateTime.class);

    public final StringPath major = createString("major");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final com.cnco.campusflow.image.QImageEntity profileImg;

    public final ListPath<com.cnco.campusflow.timetable.TimetableEntity, com.cnco.campusflow.timetable.QTimetableEntity> timetables = this.<com.cnco.campusflow.timetable.TimetableEntity, com.cnco.campusflow.timetable.QTimetableEntity>createList("timetables", com.cnco.campusflow.timetable.TimetableEntity.class, com.cnco.campusflow.timetable.QTimetableEntity.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTimestamp = _super.updateTimestamp;

    public final StringPath userId = createString("userId");

    public final StringPath userStatus = createString("userStatus");

    public QAppUserEntity(String variable) {
        this(AppUserEntity.class, forVariable(variable), INITS);
    }

    public QAppUserEntity(Path<? extends AppUserEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAppUserEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAppUserEntity(PathMetadata metadata, PathInits inits) {
        this(AppUserEntity.class, metadata, inits);
    }

    public QAppUserEntity(Class<? extends AppUserEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.college = inits.isInitialized("college") ? new com.cnco.campusflow.college.QCollegeEntity(forProperty("college")) : null;
        this.collegeImg = inits.isInitialized("collegeImg") ? new com.cnco.campusflow.image.QImageEntity(forProperty("collegeImg"), inits.get("collegeImg")) : null;
        this.profileImg = inits.isInitialized("profileImg") ? new com.cnco.campusflow.image.QImageEntity(forProperty("profileImg"), inits.get("profileImg")) : null;
    }

}

