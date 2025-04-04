package com.cnco.campusflow.menu;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class FavoriteMenuResponseDto {

    private Long favMenuId;
    private MenuResponseDto menu;
}

