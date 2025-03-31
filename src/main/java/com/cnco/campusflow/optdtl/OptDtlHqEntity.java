package com.cnco.campusflow.optdtl;


import com.cnco.campusflow.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "opt_dtl_hq", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OptDtlHqEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long optDtlHqId;

    @Column
    private String opDtlHqNm;

    @Column
    private Integer min;
    @Column
    private Integer max;
    @Column
    private Integer price;
    @Column
    private String type;
    @Column
    private Integer unitPrice;



}

