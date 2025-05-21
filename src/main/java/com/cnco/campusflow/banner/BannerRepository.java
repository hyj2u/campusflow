package com.cnco.campusflow.banner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BannerRepository extends JpaRepository<BannerEntity, Long>, CustomBannerRepository {
    boolean existsByBannerNm(String bannerNm);
}

