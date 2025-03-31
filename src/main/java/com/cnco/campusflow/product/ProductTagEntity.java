package com.cnco.campusflow.product;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.store.StoreEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_tag", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProductTagEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long productTagId;

    @Column
    private String productTagNm;
    @Column
    private String showYn;
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;
    @ManyToOne
    @JoinColumn( name = "product_tag_hq_id", nullable = false)
    private ProductTagHqEntity productTagHqEntity;


}
