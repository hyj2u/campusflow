package com.cnco.campusflow.store;


import com.cnco.campusflow.brand.BrandEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.image.ImageEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Table(name = "store", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class StoreEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long storeId;
    @Column
    private String storeNm;

    @Column
    private String addressMain;

    @Column
    private String approveYn;

    @Column
    private String storeCd;

    @Column
    private LocalTime dayOpenTm;

    @Column
    private LocalTime dayCloseTm;

    @Column
    private LocalTime satOpenTm;

    @Column
    private LocalTime satCloseTm;

    @Column
    private LocalTime sunOpenTm;

    @Column
    private LocalTime sunCloseTm;

    @Column
    private String addressDtl;

    @Column
    private Double couponRate;

    @Column
    private Double discountRate;

    @Column
    private Double pointRate;

    @Column
    private String mobilePhone;

    @Column
    private String mainPhone;

    @Column
    private String managerPhone;

    @Column
    private String deliveryYn;

    @Column
    private String togoYn;

    @Column
    private String inhereYn;

    @Column
    private String stampYn;

    @Column
    private String secretMoneyYn;

    @Column
    private String introduction;


    @Column
    private String kcpCorp;

    @Column
    private String kcpGen;

    @Column
    private String deliveryNote;
    @Column
    private int stampNum;

    @Column
    private String orderReservYn;

    @Column
    private String openYn;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandEntity brand; // N:1 관계

    @Column
    private String owner;
    @OneToOne
    @JoinColumn( referencedColumnName = "imageId", nullable = true)
    private ImageEntity mainImg; // Main Image 매핑
    @Column
    private Long qrImg;
    @Column
    private String email;
    @Column
    private Double latitude; // 위도

    @Column
    private Double longitude; // 경도
}

