package com.cnco.campusflow.image;

import com.cnco.campusflow.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "image", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ImageEntity extends com.cnco.campusflow.common.BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long imageId;
    @Column
    private String imgNm;

    @Column
    private String imgPath;

}

