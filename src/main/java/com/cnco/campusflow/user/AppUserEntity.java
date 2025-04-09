package com.cnco.campusflow.user;

import com.cnco.campusflow.college.CollegeEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.image.ImageEntity;
import com.cnco.campusflow.timetable.TimetableEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "app_user", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserEntity extends BaseEntity implements UserDetails {
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "app_user_id") // timetable 테이블에 외래키 컬럼 생성
    private List<TimetableEntity> timetables;
    @Column
    private LocalDateTime lastLoginDt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
