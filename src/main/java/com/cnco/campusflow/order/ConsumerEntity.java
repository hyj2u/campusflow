package com.cnco.campusflow.order;


import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "consumer", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ConsumerEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long consumerId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id")
    private AppUserEntity appUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_addr_id")
    private OrderAddressEntity orderAddress;
    @Column
    private String storeDemand;

    @Column
    private String deliveryDemand;

}

