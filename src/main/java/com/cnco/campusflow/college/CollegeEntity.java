package com.cnco.campusflow.college;

import com.cnco.campusflow.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "college", schema = "admin")
public class CollegeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer collegeId;

    @Column(unique = true)
    private String collegeCode;
    @Column
    private String collegeName;


}
