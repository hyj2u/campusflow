package com.cnco.campusflow.news;

import com.cnco.campusflow.code.CodeEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "today_news", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class NewsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long newsId;
    @Column
    private String url;
    @Column
    private String activeYn;

}

