package com.cnco.campusflow.terms;

import com.cnco.campusflow.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "terms", schema = "admin")
@Data
@Schema(
    name = "TermsEntity",
    description = "서비스 약관 엔티티"
)
public class TermsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    @Schema(description = "약관 ID", example = "1")
    private Long boardId;

    @Column(nullable = false)
    @Schema(description = "서비스명", example = "main")
    private String service;

    @Column(columnDefinition = "TEXT", nullable = false)
    @Schema(description = "약관 내용", example = "서비스 이용 약관 전문...")
    private String termsContent;
} 