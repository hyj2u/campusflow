package com.cnco.campusflow.community;

import com.cnco.campusflow.code.CodeEntity;
import com.cnco.campusflow.college.CollegeEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.image.ImageEntity;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "community_board", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CommunityBoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long boardId;   // 게시글 번호

    @Column
    private String title;   // 제목
    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUserEntity appUser;

    @Column
    private Integer viewCnt;    // 조회(수)


    @Column
    private String secretYn;    // 비밀글여부
    @ManyToOne
    @JoinColumn(name="code_cd")
    private CodeEntity boardType;
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "board_id")
    private List<ImageEntity> images; // 1:N 관계

}
