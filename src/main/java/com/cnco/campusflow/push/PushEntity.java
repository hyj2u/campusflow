package com.cnco.campusflow.push;


import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.image.ImageEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

// Push 서비스
@Entity
@Table(name = "push", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)  
public class PushEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pushId;
    @Column
    private LocalDateTime scheduledAt;  // 발송 예정일시
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
    private String sendStatus = "PENDING";  
    @Column
    private String channel;    // 채널
    @Column(length = 1, nullable = false)
    private Character targetAllYn = 'Y';  // 대상 전체여부
    @Column(length = 1, nullable = false)
    private Character activeYn = 'Y';  // 활성화 여부
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private String link;
    @Column
    private String sendType;      // 발송 유형 (P: 푸시, A: 알림톡)

    @OneToOne
    @JoinColumn( referencedColumnName = "imageId", nullable = true)
    private ImageEntity pushImg; // Push Image 매핑


}
