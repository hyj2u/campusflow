package com.cnco.campusflow.community;

import com.cnco.campusflow.code.CodeEntity;
import com.cnco.campusflow.college.CollegeEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "reply", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ReplyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long replyId;
    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private CommunityBoardEntity board;
    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUserEntity appUser;

    @Column
    private Integer level;

    @Column
    private Long upTreeId;

    @Column
    private Long blindYn;

    @Column
    private Long likeCnt;

}
