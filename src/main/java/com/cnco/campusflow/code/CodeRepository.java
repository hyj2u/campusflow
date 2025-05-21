package com.cnco.campusflow.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<CodeEntity, Long> {
    Optional<CodeEntity> findByCodeCd(String codeCd);

    List<CodeEntity> findByCodeCatAndActiveYnOrderByCodeOrderAsc(String codeCat, String activeYn);

    CodeEntity findByCodeCatAndCodeCdAndActiveYn(String codeCat, String codeCd, String activeYn);

    boolean existsByCodeCatAndCodeCdAndActiveYn(String codeCat, String codeCd, String activeYn);
}
