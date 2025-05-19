package com.cnco.campusflow.push;


import com.cnco.campusflow.code.CodeEntity;
import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.sendinfo.SendInfoEntity;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 앱 사용자 푸시 정보를 저장하는 엔티티 클래스
 * 
 * 주요 필드:
 * - appUserPushId: 앱 사용자 푸시 ID (PK)
 * - push: 푸시 정보
 * - appUser: 앱 사용자 정보
 * - sendInfo: 발송 정보
 * - activeYn: 활성화 여부
 */
@Entity
@Table(name = "app_user_push", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper=false)
public class AppUserPushEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long appUserPushId;    // 앱 사용자 푸시 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "push_id")
    private PushEntity push;       // 푸시 정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUserEntity appUser; // 앱 사용자 정보

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_id")
    private SendInfoEntity sendInfo; // 발송 정보

    @Column(nullable = false, length = 1)
    private String activeYn = "Y";  // 활성화 여부 (Y: 활성화, N: 비활성화), 기본값 Y

    @Column
    private LocalDateTime sendDttm;
    @Column
    private String sendStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_type_id")
    private CodeEntity sendType;
    @Column
    private String sendSubType;
} 