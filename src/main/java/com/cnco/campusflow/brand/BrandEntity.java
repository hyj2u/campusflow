package com.cnco.campusflow.brand;


import com.cnco.campusflow.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "brand", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BrandEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long brandId;

    @Column
    private String brandNm;
    @Column
    private Integer orderNum;

}
