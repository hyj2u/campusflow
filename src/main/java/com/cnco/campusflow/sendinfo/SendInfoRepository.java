package com.cnco.campusflow.sendinfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SendInfoRepository extends JpaRepository<SendInfoEntity, Long> {

}
