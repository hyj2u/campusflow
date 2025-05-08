package com.cnco.campusflow.store;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "favorite_store", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "FavoriteStoreEntity",
    description = """
        즐겨찾기 매장 엔티티
        
        * 사용자가 즐겨찾기한 매장 정보를 저장합니다.
        * 사용자와 매장 정보를 연결합니다.
        * 생성/수정 시간이 자동으로 기록됩니다.
        """,
    example = """
        {
            "fav_store_id": 1,
            "user": {
                "userId": 1,
                "nickname": "홍길동"
            },
            "store": {
                "storeId": 1,
                "storeNm": "강남점"
            }
        }
        """
)
public class FavoriteStoreEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "즐겨찾기 매장 번호", example = "1")
    private Long fav_store_id;

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
    @JoinColumn(name = "store_id", nullable = false)
    @Schema(description = "매장 정보", example = """
        {
            "storeId": 1,
            "storeNm": "강남점"
        }
        """)
    private StoreEntity store;
}

