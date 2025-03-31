package com.cnco.campusflow.product;


import com.cnco.campusflow.category.CategoryHqEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.image.ImageEntity;
import com.cnco.campusflow.optgrp.OptGrpHqEntity;
import com.cnco.campusflow.option.OptionHqEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product_hq", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProductHqEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long productHqId;

    @Column
    private String productHqNm;
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
    @JoinColumn(name = "product_hq_id") // 외래키를 ProductTagHqEntity 테이블에 설정
    private List<ProductTagHqEntity> productTags; // 1:N 관계
    @ManyToMany
    @JoinTable(
            name = "category_product_hq_mapp", // 매핑 테이블 이름
            schema = "admin",
            joinColumns = @JoinColumn(name = "product_hq_id"),
            inverseJoinColumns = @JoinColumn(name = "category_hq_id")
    )
    private Set<CategoryHqEntity> categories = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_opt_grp_hq_mapp", // 매핑 테이블 이름
            schema = "admin",
            joinColumns = @JoinColumn(name = "product_hq_id"),
            inverseJoinColumns = @JoinColumn(name = "opt_grp_hq_id")
    )
    private Set<OptGrpHqEntity> optGrp = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_option_hq_mapp", // 매핑 테이블 이름
            schema = "admin",
            joinColumns = @JoinColumn(name = "product_hq_id"),
            inverseJoinColumns = @JoinColumn(name = "option_hq_id")
    )
    private Set<OptionHqEntity> options = new HashSet<>();
}

