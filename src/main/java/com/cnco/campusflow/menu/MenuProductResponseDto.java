package com.cnco.campusflow.menu;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class MenuProductResponseDto {
    private Long productId;
    private String productNm;
    private Integer price;
    private String mainImageUrl; // 메인 이미지 URL
}
