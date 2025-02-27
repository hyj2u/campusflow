package com.cnco.campusflow.timetable;

import com.cnco.campusflow.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "timetable", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TimetableEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long tmTableId;
    @Column
    private String tmTableName;

    @Column
    private String category;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tmtable_id")
    private List<CourseEntity> courses;

}

