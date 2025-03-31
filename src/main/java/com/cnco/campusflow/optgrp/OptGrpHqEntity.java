package com.cnco.campusflow.optgrp;

import com.cnco.campusflow.brand.BrandEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "opt_grp_hq", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OptGrpHqEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long optGrpHqId;

    @Column
    private String optGrpHqNm;
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false) // 브랜드 FK
    private BrandEntity brand;
    @Column
    private String orderNum;


}

