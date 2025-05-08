package com.cnco.campusflow.order;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.menu.MenuEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "order", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "OrderEntity",
    description = """
        주문 엔티티
        
        * 사용자의 주문 정보를 저장합니다.
        * 주문자 정보, 주문 메뉴 목록, 총 가격, 주문 상태를 관리합니다.
        * 생성/수정 시간이 자동으로 기록됩니다.
        """,
    example = """
        {
            "orderId": 1,
            "consumer": {
                "consumerId": 1,
                "name": "홍길동",
                "phone": "010-1234-5678"
            },
            "menus": [
                {
                    "menuId": 1,
                    "product": {
                        "productId": 1,
                        "productNm": "아메리카노"
                    }
                }
            ],
            "totalPrice": 4500,
            "orderStatus": "ORDERED"
        }
        """
)
public class OrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "주문 번호", example = "1")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    @Schema(description = "주문자 정보", example = """
        {
            "consumerId": 1,
            "name": "홍길동",
            "phone": "010-1234-5678"
        }
        """)
    private ConsumerEntity consumer;

    @OneToMany
    @Schema(description = "주문 메뉴 목록", example = """
        [
            {
                "menuId": 1,
                "product": {
                    "productId": 1,
                    "productNm": "아메리카노"
                }
            }
        ]
        """)
    private List<MenuEntity> menus;

    @Schema(description = "주문 총 가격", example = "4500")
    private Integer totalPrice;

    @Schema(description = "주문 상태", example = "ORDERED", allowableValues = {"ORDERED", "PREPARING", "READY", "COMPLETED", "CANCELLED"})
    private String orderStatus;
}

