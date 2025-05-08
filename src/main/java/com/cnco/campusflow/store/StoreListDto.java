package com.cnco.campusflow.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "StoreListDto",
    description = """
        매장 목록 DTO
        
        * 매장 목록 조회 결과를 담습니다.
        * 매장의 기본 정보, 위치, 영업시간, 서비스 제공 여부를 포함합니다.
        """,
    example = """
        {
            "storeId": 1,
            "storeNm": "강남점",
            "owner": "홍길동",
            "addressMain": "서울시 강남구",
            "addressDtl": "123-45",
            "distance": 0.5,
            "dayOpenTm": "09:00",
            "dayCloseTm": "22:00",
            "satOpenTm": "09:00",
            "satCloseTm": "22:00",
            "sunOpenTm": "09:00",
            "sunCloseTm": "22:00",
            "deliveryYn": "Y",
            "togoYn": "Y",
            "inhereYn": "Y"
        }
        """
)
public class StoreListDto {
    @Schema(description = "매장 번호", example = "1")
    private Long storeId;

    @Schema(description = "매장명", example = "강남점")
    private String storeNm;

    @Schema(description = "매장 대표자", example = "홍길동")
    private String owner;

    @Schema(description = "기본 주소", example = "서울시 강남구")
    private String addressMain;

    @Schema(description = "상세 주소", example = "123-45")
    private String addressDtl;

    @Schema(description = "현재 위치로부터의 거리(km)", example = "0.5")
    private Double distance;

    @Schema(description = "평일 오픈 시간", example = "09:00")
    private LocalTime dayOpenTm;

    @Schema(description = "평일 마감 시간", example = "22:00")
    private LocalTime dayCloseTm;

    @Schema(description = "토요일 오픈 시간", example = "09:00")
    private LocalTime satOpenTm;

    @Schema(description = "토요일 마감 시간", example = "22:00")
    private LocalTime satCloseTm;

    @Schema(description = "일요일 오픈 시간", example = "09:00")
    private LocalTime sunOpenTm;

    @Schema(description = "일요일 마감 시간", example = "22:00")
    private LocalTime sunCloseTm;

    @Schema(description = "배달 서비스 제공 여부", example = "Y", allowableValues = {"Y", "N"})
    private String deliveryYn;

    @Schema(description = "포장 서비스 제공 여부", example = "Y", allowableValues = {"Y", "N"})
    private String togoYn;

    @Schema(description = "매장 내 식사 서비스 제공 여부", example = "Y", allowableValues = {"Y", "N"})
    private String inhereYn;
}
