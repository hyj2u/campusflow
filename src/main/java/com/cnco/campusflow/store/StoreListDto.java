package com.cnco.campusflow.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreListDto {
    private Long storeId;
    private String storeNm;
    private String owner;

    private String addressMain;
    private String addressDtl;

    private Double distance;

    private LocalTime dayOpenTm;
    private LocalTime dayCloseTm;
    private LocalTime satOpenTm;
    private LocalTime satCloseTm;
    private LocalTime sunOpenTm;
    private LocalTime sunCloseTm;

    private String deliveryYn;
    private String togoYn;
    private String inhereYn;


}
