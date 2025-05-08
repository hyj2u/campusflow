package com.cnco.campusflow.menu;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.optdtl.OptDtlEntity;
import com.cnco.campusflow.option.OptionEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "menu_option", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "MenuOptionEntity",
    description = """
        메뉴 옵션 엔티티
        
        * 메뉴의 옵션 정보를 저장합니다.
        * 옵션과 옵션 상세 정보를 연결하고, 선택 수량과 가격을 관리합니다.
        * 생성/수정 시간이 자동으로 기록됩니다.
        """,
    example = """
        {
            "menuOptId": 1,
            "optionEntity": {
                "optionId": 1,
                "optionNm": "샷 추가"
            },
            "optDtlEntity": {
                "optDtlId": 1,
                "optDtlNm": "에스프레소 샷"
            },
            "chosenNum": 2,
            "totalPrice": 1000
        }
        """
)
public class MenuOptionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "메뉴 옵션 번호", example = "1")
    private Long menuOptId;

    @ManyToOne
    @JoinColumn(name = "option_id", nullable = false)
    @Schema(description = "옵션 정보", example = """
        {
            "optionId": 1,
            "optionNm": "샷 추가"
        }
        """)
    private OptionEntity optionEntity;

    @ManyToOne
    @JoinColumn(name = "opt_dtl_id", nullable = false)
    @Schema(description = "옵션 상세 정보", example = """
        {
            "optDtlId": 1,
            "optDtlNm": "에스프레소 샷"
        }
        """)
    private OptDtlEntity optDtlEntity;

    @Schema(description = "선택된 옵션 수량", example = "2")
    private Integer chosenNum;

    @Schema(description = "옵션 총 가격", example = "1000")
    private Integer totalPrice;
}

