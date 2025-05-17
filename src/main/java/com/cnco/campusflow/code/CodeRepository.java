package com.cnco.campusflow.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<CodeEntity, Long> {
    Optional<CodeEntity> findByCodeCd(String codeCd);
}
