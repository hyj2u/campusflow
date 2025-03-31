package com.cnco.campusflow.option;

import com.cnco.campusflow.code.CodeEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.optdtl.OptDtlHqEntity;
import com.cnco.campusflow.optgrp.OptGrpHqEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "option_hq", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OptionHqEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long optionHqId;

    @Column
    private String optionHqNm;
    @ManyToOne
    @JoinColumn(name = "opt_grp_hq_id")
    private OptGrpHqEntity optGrp;
    @ManyToOne
    @JoinColumn(name = "code_id", nullable = false)
    private CodeEntity code;
    @Column
    private String requireYn;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "option_hq_id") // 외래키 매핑
    private List<OptDtlHqEntity> options= new ArrayList<>(); // 세부 옵션 리스트


}

