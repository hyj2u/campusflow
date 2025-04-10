package com.cnco.campusflow.order;


import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.menu.MenuEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "order", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    private ConsumerEntity consumer;
    @OneToMany
    private List<MenuEntity> menus;

    private Integer totalPrice;
    private String orderStatus;

}

