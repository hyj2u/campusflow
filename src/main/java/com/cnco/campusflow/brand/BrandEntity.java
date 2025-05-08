package com.cnco.campusflow.brand;

import com.cnco.campusflow.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "brand", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    description = "브랜드 엔티티",
    example = """
        {
            "brandId": 1,
            "brandNm": "블루포트",
            "orderNum": 2
        }
        """
)
public class BrandEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "브랜드 ID", example = "1")
    private Long brandId;

    @Column
    @Schema(description = "브랜드명", example = "블루포트", required = true)
    private String brandNm;

    @Column
    @Schema(description = "정렬 순서", example = "1", required = true)
    private Integer orderNum;
}
