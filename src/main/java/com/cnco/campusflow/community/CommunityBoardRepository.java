package com.cnco.campusflow.community;


import com.cnco.campusflow.image.ImageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityBoardRepository extends JpaRepository<CommunityBoardEntity, Long>, CustomCommunityBoardRepository {

}
