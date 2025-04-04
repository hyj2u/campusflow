package com.cnco.campusflow.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRepository extends JpaRepository<ConsumerEntity, Long>{

    public ConsumerEntity findByAppUserAppUserId(Long appUserId);
    public ConsumerEntity findByOrderAddressOrderAddrId(Long addressId);
}
