package com.cnco.campusflow.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{

    List<OrderEntity> findTop10ByConsumerAppUserAppUserIdOrderByOrderIdDesc(Long appUserId);

    List<OrderEntity> findAllByConsumerAppUserAppUserIdOrderByOrderIdDesc(Long appUserId);
}
