package com.cnco.campusflow.user;

import com.cnco.campusflow.college.CollegeEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.image.ImageEntity;
import com.cnco.campusflow.timetable.TimetableEntity;
import io.swagger.v3.oas.annotations.media.Schema;
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
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "AppUserEntity",
    description = """
        사용자 엔티티
        
        * 사용자의 기본 정보를 저장합니다.
        * 프로필 이미지와 대학 이미지 URL을 포함합니다.
        * 시간표와 스토어 정보와 연관관계를 가집니다.
        """,
    example = """
        {
            "appUserId": 1,
            "userId": "user123",
            "nickname": "닉네임123",
            "phone": "01012345678",
            "profileImgUrl": "https://example.com/profile.jpg",
            "collegeImgUrl": "https://example.com/college.jpg"
        }
        """
)
public class AppUserEntity extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "사용자 ID", example = "1")
    private Long appUserId;

    @Column(unique = true)
    @Schema(description = "로그인 아이디", example = "user123")
    private String userId;
    @Column
    @NotBlank
    private String password;

    @Column(unique = true)
    @Schema(description = "사용자 닉네임", example = "닉네임123")
    private String nickname;

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
    @Schema(description = "전화번호", example = "01012345678")
    private String phone;
    @Column
    private String appUserName;
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
    @Schema(description = "사용자의 시간표 목록")
    private List<TimetableEntity> timetables;
    @Column
    private LocalDateTime lastLoginDt;
    @Column
    private String collegeAdmissionYear;

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
