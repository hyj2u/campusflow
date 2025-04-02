package com.cnco.campusflow.menu;


import com.cnco.campusflow.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "favorite_menu", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class FavoriteMenuResponseDto extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favMenuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private MenuEntity menu;
}

