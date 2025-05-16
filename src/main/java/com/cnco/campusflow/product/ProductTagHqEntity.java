package com.cnco.campusflow.product;

import com.cnco.campusflow.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_tag_hq", schema = "admin")
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProductTagHqEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long productTagHqId;

    @Column
    private String productTagHqNm;
    @Column
    private String showYn;



}
