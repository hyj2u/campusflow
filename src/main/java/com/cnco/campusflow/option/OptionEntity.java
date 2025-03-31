package com.cnco.campusflow.option;


import com.cnco.campusflow.code.CodeEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.optdtl.OptDtlEntity;
import com.cnco.campusflow.optgrp.OptGrpEntity;
import com.cnco.campusflow.store.StoreEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "option", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OptionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long optionId;

    @Column
    private String optionNm;
    @ManyToOne
    @JoinColumn(name = "opt_grp_id")
    private OptGrpEntity optGrp;
    @ManyToOne
    @JoinColumn(name = "code_id", nullable = false)
    private CodeEntity code;
    @Column
    private String requireYn;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "option_id") // 외래키 매핑
    private List<OptDtlEntity> options; // 세부 옵션 리스트
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;
    @ManyToOne
    @JoinColumn( name = "option_hq_id", nullable = false)
    private OptionHqEntity optionHq;



}

