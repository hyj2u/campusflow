package com.cnco.campusflow.image;

import com.cnco.campusflow.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "image", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "ImageEntity",
    description = """
        이미지 엔티티
        
        * 이미지의 기본 정보를 저장합니다.
        * 이미지 ID, 이름, 경로 정보를 포함합니다.
        * 생성/수정 시간이 자동으로 기록됩니다.
        """,
    example = """
        {
            "imageId": 1,
            "imgNm": "example.jpg",
            "imgPath": "/images/2024/03/example.jpg"
        }
        """
)
public class ImageEntity extends com.cnco.campusflow.common.BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "이미지 번호", example = "1")
    private Long imageId;

    @Column
    @Schema(description = "이미지 파일명", example = "example.jpg")
    private String imgNm;

    @Column
    @Schema(description = "이미지 저장 경로", example = "/images/2024/03/example.jpg")
    private String imgPath;
}

