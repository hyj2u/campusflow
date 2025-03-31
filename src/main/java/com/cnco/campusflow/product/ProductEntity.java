package com.cnco.campusflow.product;

import com.cnco.campusflow.category.CategoryEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.image.ImageEntity;
import com.cnco.campusflow.optgrp.OptGrpEntity;
import com.cnco.campusflow.option.OptionEntity;
import com.cnco.campusflow.store.StoreEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long productId;

    @Column
    private String productNm;
    @ManyToOne
    @JoinColumn( name = "main_image_id")
    private ImageEntity mainImg; // Main Image 매핑

    @Column
    private String productHqEngNm;
    @Column
    private Integer price;
    @Column
    private Integer buyCnt;
    @Column
    private String activeYn;
    @Column
    private String stampUseYn;
    @Column
    private Integer stampUseCnt;
    @Column
    private String appVisibleYn;
    @Column
    private String kioskVisibleYn;
    @Column
    private String productDtl;
    @Column
    private String originInfo;
    @ManyToOne
    @JoinColumn( name = "origin_image_id")
    private ImageEntity originImg;
    @Column
    private String allergyInfo;
    @ManyToOne
    @JoinColumn( name = "allergy_image_id")
    private ImageEntity allergyImg;

    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<ProductTagEntity> productTags; // 1:N 관계
    @ManyToMany
    @JoinTable(
            name = "category_product_mapp", // 매핑 테이블 이름
            schema = "admin",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<CategoryEntity> categories = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(
            name = "product_option_mapp", // 매핑 테이블 이름
            schema = "admin",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id")
    )
    private Set<OptionEntity> options = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_opt_grp_mapp", // 매핑 테이블 이름
            schema = "admin",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "opt_grp_id")
    )
    private Set<OptGrpEntity> optGrp = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;
    @ManyToOne
    @JoinColumn( name = "product_hq_id", nullable = false)
    private ProductHqEntity productHqEntity;
}

