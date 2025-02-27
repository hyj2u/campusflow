package com.cnco.campusflow.code;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<CodeEntity, Long>  {
    CodeEntity findByCodeCd(String codeCd);
}
