package com.cnco.campusflow.menu;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.user.AppUserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "favorite_menu", schema = "admin")
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "FavoriteMenuEntity",
    description = """
        즐겨찾기 메뉴 엔티티
        
        * 사용자가 즐겨찾기한 메뉴 정보를 저장합니다.
        * 사용자와 메뉴 정보를 연결합니다.
        * 생성/수정 시간이 자동으로 기록됩니다.
        """,
    example = """
        {
            "favMenuId": 1,
            "user": {
                "userId": 1,
                "nickname": "홍길동"
            },
            "menu": {
                "menuId": 1,
                "product": {
                    "productId": 1,
                    "productNm": "아메리카노"
                }
            }
        }
        """
)
public class FavoriteMenuEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "즐겨찾기 메뉴 번호", example = "1")
    private Long favMenuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "사용자 정보", example = """
        {
            "userId": 1,
            "nickname": "홍길동"
        }
        """)
    private AppUserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @Schema(description = "메뉴 정보", example = """
        {
            "menuId": 1,
            "product": {
                "productId": 1,
                "productNm": "아메리카노"
            }
        }
        """)
    private MenuEntity menu;
}

