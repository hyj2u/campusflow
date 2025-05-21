package com.cnco.campusflow.bannertype;

import com.cnco.campusflow.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;   // JSON 변환 시 null 값 필드는 제외
import jakarta.persistence.*;
import lombok.Data;

// 배너 종류 관리
@Entity
@Table(name = "bannerType", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BannerTypeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long bannerTypeId;
    @Column(nullable = false, length = 255)
    private String bannerTypeCd; // 배너키

    @Column(nullable = false, length = 255)
    private String bannerTypeNm; // 배너명

    @Column(nullable = false)
    private Integer bannerTypeWt; // 위치(Width)

    @Column(nullable = false)
    private Integer bannerTypeHt; // 위치(Height)

    @Column(nullable = false)
    private String bannerTypeAction;  // Code 테이블에서 code_cat = 'banner_type_action'인 리스트 중 선택

    @Column(nullable = false, length = 1)
    private Character activeYn; // 사용여부

}