package com.cnco.campusflow.category;


import com.cnco.campusflow.brand.BrandEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.store.StoreEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "category", schema = "admin")
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CategoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long categoryId;

    @Column
    private String categoryNm;
    @Column
    private Integer orderNum;
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false) // 브랜드 FK
    private BrandEntity brand;
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;
    @ManyToOne
    @JoinColumn(name = "category_hq_id", nullable = false)
    private CategoryHqEntity categoryHqEntity;
}
