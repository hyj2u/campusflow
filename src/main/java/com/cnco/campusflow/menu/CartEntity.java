package com.cnco.campusflow.menu;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.user.AppUserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "cart", schema = "admin")
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
        name = "CartEntity",
        description = """
                카트 엔티티
                
                * 회원의 장바구니 정보를 저장합니다.
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
public class CartEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "장바구니 key", example = "1")
    private Long cartId;

    @OneToMany(orphanRemoval = false)
    @JoinColumn(name = "cart_id")
    @Schema(description = "메뉴 정보", example = """
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
            """)
    private List<MenuEntity> menus;
    @OneToOne
    @JoinColumn(name = "app_user_id")
    @Schema(description = "사용자 정보", example = """
                 {
                "appUserId": 1,
                "userId": "user123",
                "nickname": "닉네임123",
                "phone": "01012345678",
                "profileImgUrl": "https://example.com/profile.jpg",
                "collegeImgUrl": "https://example.com/college.jpg"
            }
            """)
    private AppUserEntity appUser;


}

