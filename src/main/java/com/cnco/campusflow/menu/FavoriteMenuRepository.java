package com.cnco.campusflow.menu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteMenuRepository extends JpaRepository<FavoriteMenuEntity, Long> {
    List<FavoriteMenuEntity> findAllByUserAppUserIdAndMenuStoreStoreId(Long userId, Long menuStoreId);
}
