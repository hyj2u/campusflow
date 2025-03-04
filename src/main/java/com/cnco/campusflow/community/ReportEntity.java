package com.cnco.campusflow.community;

import com.cnco.campusflow.common.BaseEntity;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "board_report", schema = "admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ReportEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long reportId;
    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private CommunityBoardEntity board;
    @Column
    private String reason;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUserEntity appUser;


}
