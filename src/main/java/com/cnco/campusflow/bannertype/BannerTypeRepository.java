package com.cnco.campusflow.bannertype;

//import com.cnco.admin.member.MemberMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerTypeRepository extends JpaRepository<BannerTypeEntity, Long> {
    boolean existsByBannerTypeCd(String bannerTypeCd);
    boolean existsByBannerTypeNm(String bannerTypeNm);

    List<BannerTypeEntity> findByActiveYn(Character activeYn);

}

