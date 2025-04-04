package com.cnco.campusflow.order;


import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_address", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OrderAddressEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long orderAddrId;
    @Column
    private String addressMain;

    @Column
    private String addressDtl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUserEntity appUser;
    @Column
    private String defaultYn;


}

