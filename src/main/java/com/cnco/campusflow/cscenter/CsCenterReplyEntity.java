package com.cnco.campusflow.cscenter;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.user.AppUserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cscenter_reply", schema = "admin")
@Data
public class CsCenterReplyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long replyId;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private CsCenterBoardEntity board;

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
    private Integer likeCnt;

    @Column
    private String deleteYn;

    @Column
    private String blindYn;
} 