package com.cnco.campusflow.store;

import com.cnco.campusflow.brand.BrandEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.image.ImageEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Table(name = "store", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "StoreEntity",
    description = """
        매장 엔티티
        
        * 매장의 기본 정보를 저장합니다.
        * 매장명, 주소, 영업시간, 연락처, 서비스 제공 여부 등을 관리합니다.
        * 브랜드 정보와 메인 이미지를 연결합니다.
        * 생성/수정 시간이 자동으로 기록됩니다.
        """,
    example = """
        {
            "storeId": 1,
            "storeNm": "강남점",
            "addressMain": "서울시 강남구",
            "addressDtl": "123-45",
            "approveYn": "Y",
            "storeCd": "STORE001",
            "dayOpenTm": "09:00",
            "dayCloseTm": "22:00",
            "deliveryYn": "Y",
            "togoYn": "Y",
            "inhereYn": "Y",
            "mainPhone": "02-1234-5678",
            "brand": {
                "brandId": 1,
                "brandNm": "스타벅스"
            }
        }
        """
)
public class StoreEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "매장 번호", example = "1")
    private Long storeId;

    @Column
    @Schema(description = "매장명", example = "강남점")
    private String storeNm;

    @Column
    @Schema(description = "기본 주소", example = "서울시 강남구")
    private String addressMain;

    @Column
    @Schema(description = "승인 여부", example = "Y", allowableValues = {"Y", "N"})
    private String approveYn;

    @Column
    @Schema(description = "매장 코드", example = "STORE001")
    private String storeCd;

    @Column
    @Schema(description = "평일 오픈 시간", example = "09:00")
    private LocalTime dayOpenTm;

    @Column
    @Schema(description = "평일 마감 시간", example = "22:00")
    private LocalTime dayCloseTm;

    @Column
    @Schema(description = "토요일 오픈 시간", example = "09:00")
    private LocalTime satOpenTm;

    @Column
    @Schema(description = "토요일 마감 시간", example = "22:00")
    private LocalTime satCloseTm;

    @Column
    @Schema(description = "일요일 오픈 시간", example = "09:00")
    private LocalTime sunOpenTm;

    @Column
    @Schema(description = "일요일 마감 시간", example = "22:00")
    private LocalTime sunCloseTm;

    @Column
    @Schema(description = "상세 주소", example = "123-45")
    private String addressDtl;

    @Column
    @Schema(description = "쿠폰 적립률", example = "0.05")
    private Double couponRate;

    @Column
    @Schema(description = "할인율", example = "0.1")
    private Double discountRate;

    @Column
    @Schema(description = "포인트 적립률", example = "0.01")
    private Double pointRate;

    @Column
    @Schema(description = "휴대폰 번호", example = "010-1234-5678")
    private String mobilePhone;

    @Column
    @Schema(description = "대표 전화번호", example = "02-1234-5678")
    private String mainPhone;

    @Column
    @Schema(description = "매니저 전화번호", example = "010-9876-5432")
    private String managerPhone;

    @Column
    @Schema(description = "배달 서비스 제공 여부", example = "Y", allowableValues = {"Y", "N"})
    private String deliveryYn;

    @Column
    @Schema(description = "포장 서비스 제공 여부", example = "Y", allowableValues = {"Y", "N"})
    private String togoYn;

    @Column
    @Schema(description = "매장 내 식사 서비스 제공 여부", example = "Y", allowableValues = {"Y", "N"})
    private String inhereYn;

    @Column
    @Schema(description = "스탬프 서비스 제공 여부", example = "Y", allowableValues = {"Y", "N"})
    private String stampYn;

    @Column
    @Schema(description = "비밀번호 서비스 제공 여부", example = "Y", allowableValues = {"Y", "N"})
    private String secretMoneyYn;

    @Column
    @Schema(description = "매장 소개", example = "강남역 1번 출구에서 도보 5분 거리에 위치한 매장입니다.")
    private String introduction;

    @Column
    @Schema(description = "KCP 법인카드 사용 여부", example = "Y", allowableValues = {"Y", "N"})
    private String kcpCorp;

    @Column
    @Schema(description = "KCP 일반카드 사용 여부", example = "Y", allowableValues = {"Y", "N"})
    private String kcpGen;

    @Column
    @Schema(description = "배달 관련 참고사항", example = "배달은 3km 이내에서만 가능합니다.")
    private String deliveryNote;

    @Column
    @Schema(description = "스탬프 개수", example = "10")
    private int stampNum;

    @Column
    @Schema(description = "예약 주문 가능 여부", example = "Y", allowableValues = {"Y", "N"})
    private String orderReservYn;

    @Column
    @Schema(description = "영업 여부", example = "Y", allowableValues = {"Y", "N"})
    private String openYn;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    @Schema(description = "브랜드 정보", example = """
        {
            "brandId": 1,
            "brandNm": "스타벅스"
        }
        """)
    private BrandEntity brand;

    @Column
    @Schema(description = "매장 대표자", example = "홍길동")
    private String owner;

    @OneToOne
    @JoinColumn(referencedColumnName = "imageId", nullable = true)
    @Schema(description = "메인 이미지", example = """
        {
            "imageId": 1,
            "imageUrl": "https://example.com/image.jpg"
        }
        """)
    private ImageEntity mainImg;

    @Column
    @Schema(description = "QR 이미지 번호", example = "1")
    private Long qrImg;

    @Column
    @Schema(description = "이메일", example = "store@example.com")
    private String email;

    @Column
    @Schema(description = "위도", example = "37.5665")
    private Double latitude;

    @Column
    @Schema(description = "경도", example = "126.9780")
    private Double longitude;
}

