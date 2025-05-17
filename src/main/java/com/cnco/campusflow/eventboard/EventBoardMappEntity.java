package com.cnco.campusflow.eventboard;

import com.cnco.campusflow.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "event_board_mapp", schema = "admin")
@Data
public class EventBoardMappEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mappId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private EventBoardEntity eventBoard;

    @Column(name = "store_id")
    private Long storeId;
} 