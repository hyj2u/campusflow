package com.cnco.campusflow.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderAddressRepository extends JpaRepository<OrderAddressEntity, Long>{
    List<OrderAddressEntity> findAllByAppUserAppUserId(Long appUserId);
}
