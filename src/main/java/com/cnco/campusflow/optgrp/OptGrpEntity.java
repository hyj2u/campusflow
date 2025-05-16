package com.cnco.campusflow.optgrp;


import com.cnco.campusflow.brand.BrandEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.store.StoreEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "opt_grp", schema = "admin")
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OptGrpEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long optGrpId;

    @Column
    private String optGrpNm;
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false) // 브랜드 FK
    private BrandEntity brand;
    @Column
    private String orderNum;
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;
    @ManyToOne
    @JoinColumn( name = "opt_grp_hq_id", nullable = false)
    private OptGrpHqEntity optGrpHqEntity;

}

