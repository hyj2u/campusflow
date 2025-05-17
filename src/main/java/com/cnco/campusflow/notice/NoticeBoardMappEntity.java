package com.cnco.campusflow.notice;

import com.cnco.campusflow.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "notice_board_mapp", schema = "admin")
@Data
public class NoticeBoardMappEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mappId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private NoticeBoardEntity noticeBoard;

    @Column(name = "store_id")
    private Long storeId;
} 