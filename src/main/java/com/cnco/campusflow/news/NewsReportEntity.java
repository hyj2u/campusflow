package com.cnco.campusflow.news;

import com.cnco.campusflow.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "today_news_report", schema = "admin")
@Data
public class NewsReportEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    private NewsEntity news;

    @Column(nullable = false)
    private String reportReason;

    @Column
    private String comment;

    @Column(nullable = false)
    private Long appUserId;

    @Column
    private Integer viewCnt;
} 