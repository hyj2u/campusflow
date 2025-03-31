package com.cnco.campusflow.optdtl;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.store.StoreEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "opt_dtl", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OptDtlEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long optDtlId;

    @Column
    private String opDtlNm;

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
    @Column
    private String dtlUseYn;
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;
    @ManyToOne
    @JoinColumn( name = "opt_dtl_hq_id", nullable = false)
    private OptDtlHqEntity optDtlHqEntity;



}

