package com.cnco.campusflow.sendinfo;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.user.AppUserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "send_info", schema = "admin")
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SendInfoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long sendId;
    @Column
    private String sendMsg;
    @Column
    private String sendTitle;
    @Column
    private String sendType;
    @Column
    private LocalDateTime reqSendTm;
    @ManyToOne
    @JoinColumn( name = "receiver_id")
    private AppUserEntity receiver;
    @Column
    private String phone;
    @Column
    private LocalDateTime sentTm;
    @Column
    private String sendStatus;

}

