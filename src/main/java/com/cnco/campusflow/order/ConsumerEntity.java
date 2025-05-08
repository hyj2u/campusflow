package com.cnco.campusflow.order;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "consumer", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "ConsumerEntity",
    description = """
        주문자 엔티티
        
        * 주문자의 정보를 저장합니다.
        * 사용자 정보, 배달 주소, 매장 요청사항, 배달 요청사항을 관리합니다.
        * 생성/수정 시간이 자동으로 기록됩니다.
        """,
    example = """
        {
            "consumerId": 1,
            "appUser": {
                "userId": 1,
                "nickname": "홍길동"
            },
            "orderAddress": {
                "orderAddrId": 1,
                "address": "서울시 강남구",
                "detailAddress": "123-45"
            },
            "storeDemand": "포크 2개 주세요",
            "deliveryDemand": "문 앞에 놓아주세요"
        }
        """
)
public class ConsumerEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "주문자 번호", example = "1")
    private Long consumerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id")
    @Schema(description = "사용자 정보", example = """
        {
            "userId": 1,
            "nickname": "홍길동"
        }
        """)
    private AppUserEntity appUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_addr_id")
    @Schema(description = "배달 주소 정보", example = """
        {
            "orderAddrId": 1,
            "address": "서울시 강남구",
            "detailAddress": "123-45"
        }
        """)
    private OrderAddressEntity orderAddress;

    @Column
    @Schema(description = "매장 요청사항", example = "포크 2개 주세요")
    private String storeDemand;

    @Column
    @Schema(description = "배달 요청사항", example = "문 앞에 놓아주세요")
    private String deliveryDemand;
}

