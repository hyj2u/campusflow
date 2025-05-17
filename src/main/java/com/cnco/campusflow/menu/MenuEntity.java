package com.cnco.campusflow.menu;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.product.ProductEntity;
import com.cnco.campusflow.store.StoreEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "menu", schema = "admin")
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "MenuEntity",
    description = """
        메뉴 엔티티
        
        * 매장의 메뉴 정보를 저장합니다.
        * 매장과 상품 정보를 연결하고, 메뉴 옵션을 포함합니다.
        * 생성/수정 시간이 자동으로 기록됩니다.
        """,
    example = """
        {
            "menuId": 1,
            "store": {
                "storeId": 1,
                "storeNm": "강남점"
            },
            "product": {
                "productId": 1,
                "productNm": "아메리카노"
            },
            "options": [
                {
                    "optionId": 1,
                    "optionNm": "샷 추가",
                    "optionPrice": 500
                }
            ]
        }
        """
)
public class MenuEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "메뉴 번호", example = "1")
    private Long menuId;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @Schema(description = "매장 정보", example = """
        {
            "storeId": 1,
            "storeNm": "강남점"
        }
        """)
    private StoreEntity store;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @Schema(description = "상품 정보", example = """
        {
            "productId": 1,
            "productNm": "아메리카노"
        }
        """)
    private ProductEntity product;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "menu_id")
    @Schema(description = "메뉴 옵션 목록", example = """
        [
            {
                "optionId": 1,
                "optionNm": "샷 추가",
                "optionPrice": 500
            }
        ]
        """)
    private List<MenuOptionEntity> options;
    @Column
    @Schema(description = "주문 수량", example = "1")
    private Integer orderCnt;


}

