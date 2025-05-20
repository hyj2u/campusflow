package com.cnco.campusflow.coupon;

import com.cnco.campusflow.user.AppUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserCouponRepository extends JpaRepository<AppUserCouponEntity, Long>, CustomAppUserCouponRepository {
} 