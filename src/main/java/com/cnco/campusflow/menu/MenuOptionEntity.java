package com.cnco.campusflow.menu;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.optdtl.OptDtlEntity;
import com.cnco.campusflow.option.OptionEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "menu_option", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MenuOptionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long menuOptId;
    @ManyToOne
    @JoinColumn(name = "option_id", nullable = false)
    private OptionEntity optionEntity;
    @ManyToOne
    @JoinColumn( name = "opt_dtl_id", nullable = false)
    private OptDtlEntity optDtlEntity;
    private Integer chosenNum;
    private Integer totalPrice;


}

