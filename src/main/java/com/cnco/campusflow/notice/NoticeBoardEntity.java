package com.cnco.campusflow.notice;

import com.cnco.campusflow.code.CodeEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.image.ImageEntity;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notice_board", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class NoticeBoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long boardId;   // 게시글 번호

    @Column
    private String title;   // 게시글 제목

    @Column
    private String content;  // 게시글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private AppUserEntity appUser;

    @Column
    private Integer viewCnt;    // 조회(수)

    @Column
    private String pushYn;   // 앱푸시 여부

    @Column
    private String talkYn;   // 알림톡 여부

    @ManyToOne
    @JoinColumn(name = "code_cd")
    private CodeEntity boardType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "notice_board_id")
    private List<ImageEntity> images; // 1:N 관계

    @OneToMany(mappedBy = "noticeBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeBoardMappEntity> storeMappings = new ArrayList<>();

    // 게시판 조회(GET) 할 때마다, 조회수 +1
    public void incrementViewCount() {
        if (this.viewCnt == null) {
            this.viewCnt = 0;
        }
        this.viewCnt += 1;
    }
} 