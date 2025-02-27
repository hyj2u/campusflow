package com.cnco.campusflow.timetable;

import com.cnco.campusflow.code.CodeEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "course", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CourseEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long courseId;
    @Column
    private String courseName;
    @Column
    private String startTime;
    @Column
    private String endTime;
    @Column
    private String location;
    @Column
    private String professor;
    @Column
    private String color;
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_day_mapp",                      // 조인 테이블 이름
            joinColumns = @JoinColumn(name = "course_id"),  // LectureEntity를 참조하는 외래키
            inverseJoinColumns = @JoinColumn(name = "code_id") // CodeEntity를 참조하는 외래키
    )
    private List<CodeEntity> days;

}

