package com.cnco.campusflow.cscenter;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.code.CodeEntity;
import com.cnco.campusflow.image.ImageEntity;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cscenter_board", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CsCenterBoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long boardId;   // 게시글 번호

    @Column
    private String title;   // 게시글 제목

    @Column
    private String content;  // 게시글 내용

    @Column
    private Integer viewCnt;    // 조회(수)

    @ManyToOne
    @JoinColumn(name = "code_id")
    private CodeEntity boardType;  // 문의 유형

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUserEntity appUser;  // 작성자

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageEntity> images = new ArrayList<>();  // 첨부 이미지

    // 게시판 조회(GET) 할 때마다, 조회수 +1
    public void incrementViewCount() {
        if (this.viewCnt == null) {
            this.viewCnt = 0;
        }
        this.viewCnt += 1;
    }
} 