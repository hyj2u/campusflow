package com.cnco.campusflow.gifticon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserGifticonRepository extends JpaRepository<AppUserGifticonEntity, Long>, CustomGifticonRepository {
}