package com.cnco.campusflow.category;

import com.cnco.campusflow.brand.BrandEntity;
import com.cnco.campusflow.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "category_hq", schema = "admin")
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CategoryHqEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long categoryHqId;

    @Column
    private String categoryHqNm;
    @Column
    private Integer orderNum;
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false) // 브랜드 FK
    private BrandEntity brand;
}
