package com.cnco.campusflow.oftenqna;

import com.cnco.campusflow.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "often_qna", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OftenQnaEntity extends BaseEntity {
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

    @Column
    private String category;    // 카테고리 (자유 입력)

    // 게시판 조회(GET) 할 때마다, 조회수 +1
    public void incrementViewCount() {
        if (this.viewCnt == null) {
            this.viewCnt = 0;
        }
        this.viewCnt += 1;
    }
} 