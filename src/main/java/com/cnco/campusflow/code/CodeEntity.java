package com.cnco.campusflow.code;


import com.cnco.campusflow.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "code", schema = "admin")
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CodeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long codeId;

    @Column
    private String codeNm;
    @Column
    private String codeCd;
    @Column
    private String activeYn;
    @Column
    private String codeCat;
    @Column
    private Integer codeOrder;

}
