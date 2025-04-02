package com.cnco.campusflow.menu;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.product.ProductEntity;
import com.cnco.campusflow.store.StoreEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "menu", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MenuEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long menuId;
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "menu_id") // 외래키 매핑
    private List<MenuOptionEntity> options; // 세부 옵션 리스트

}

