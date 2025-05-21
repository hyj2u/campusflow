package com.cnco.campusflow.banner;

import com.cnco.campusflow.bannertype.BannerTypeEntity;
import com.cnco.campusflow.store.StoreEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.image.ImageEntity;
import com.cnco.campusflow.brand.BrandEntity;
import com.fasterxml.jackson.annotation.JsonInclude;   // JSON 변환 시 null 값 필드는 제외
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


// 배너 종류 관리
@Entity
@Table(name = "banner", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BannerEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bannerId;
    @Column
    private String bannerNm;  // 배너명
    @Column
    private String bannerUrl;  // 배너링크
    @Column
    private LocalDate bannerStart;        // 배너기간(시작일)
    @Column
    private LocalDate bannerEnd;        // 배너기간(종료일)
    @Column(length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'Y'")
    private Character activeYn = 'Y'; // 사용여부
    @Column(length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'Y'")
    private Character fullExpYn = 'Y'; // 전체 노출 여부
    @Column
    private Integer viewCount = 0;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = true) // 브랜드 FK
    private BrandEntity brand;

    @ManyToOne
    @JoinColumn(name = "banner_type_id", nullable = false) // 배너타입 FK
    private BannerTypeEntity bannerType;

    @OneToOne
    @JoinColumn( referencedColumnName = "imageId", nullable = true)
    private ImageEntity bannerImg; // Banner Image 매핑

    @ManyToMany
    @JoinTable(
            name = "banner_store_mapp",               // 매핑 테이블 이름
            schema = "admin",                    // DB 스키마
            joinColumns = @JoinColumn(name = "banner_id"),      // BannerEntity의 FK
            inverseJoinColumns = @JoinColumn(name = "store_id") // StoreEntity의 FK
    )
    private Set<StoreEntity> stores = new HashSet<>();

    @Data
    public static class StoreInfo {
        private Long storeId;
        private String storeNm;
        private Long brandId;
    }
}
