package com.cnco.campusflow.user;

import com.cnco.campusflow.college.CollegeEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.image.ImageEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "app_user", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appUserId;

    @Column(unique = true)
    private String userId;
    @Column
    @NotBlank
    private String password;

    @Column(unique = true)
    private String nickname;

    @Column
    private String enterYear;
    // College와 N:1 관계 (여러 유저가 하나의 대학에 속함)
    @Column
    private String major;
    @ManyToOne
    @JoinColumn(name = "college_id" ) // college_id FK 설정
    private CollegeEntity college;
    @ManyToOne
    @JoinColumn(name = "profile_img_id")
    private ImageEntity profileImg;
    @Column
    private String phone;
    @Column
    private String username;
    @Column
    private Date birthday;
    @ManyToOne
    @JoinColumn(name = "college_img_id")
    private ImageEntity collegeImg;
    @Column
    private String userStatus;
    @Column
    private String approveStatus;


}
