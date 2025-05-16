package com.cnco.campusflow.order;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.user.AppUserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_address", schema = "admin")
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "OrderAddressEntity",
    description = """
        주문 주소 엔티티
        
        * 주문자의 배달 주소 정보를 저장합니다.
        * 기본 주소와 상세 주소를 관리합니다.
        * 기본 배달지 여부를 설정할 수 있습니다.
        * 생성/수정 시간이 자동으로 기록됩니다.
        """,
    example = """
        {
            "orderAddrId": 1,
            "addressMain": "서울시 강남구",
            "addressDtl": "123-45",
            "appUser": {
                "userId": 1,
                "nickname": "홍길동"
            },
            "defaultYn": "Y"
        }
        """
)
public class OrderAddressEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "주문 주소 번호", example = "1")
    private Long orderAddrId;

    @Column
    @Schema(description = "기본 주소", example = "서울시 강남구")
    private String addressMain;

    @Column
    @Schema(description = "상세 주소", example = "123-45")
    private String addressDtl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    @Schema(description = "사용자 정보", example = """
        {
            "userId": 1,
            "nickname": "홍길동"
        }
        """)
    private AppUserEntity appUser;

    @Column
    @Schema(description = "기본 배달지 여부", example = "Y", allowableValues = {"Y", "N"})
    private String defaultYn;
}

