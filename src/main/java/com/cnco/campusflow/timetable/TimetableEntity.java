package com.cnco.campusflow.timetable;

import com.cnco.campusflow.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "timetable", schema = "admin")
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "TimetableEntity",
    description = """
        시간표 엔티티
        
        * 시간표의 기본 정보를 저장합니다.
        * 시간표명, 카테고리, 강의 목록을 관리합니다.
        * 생성/수정 시간이 자동으로 기록됩니다.
        """,
    example = """
        {
            "tmTableId": 1,
            "tmTableName": "2024년 1학기 시간표",
            "category": "학부",
            "courses": [
                {
                    "courseId": 1,
                    "courseName": "자바 프로그래밍",
                    "professor": "홍길동",
                    "room": "A101"
                }
            ]
        }
        """
)
public class TimetableEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "시간표 번호", example = "1")
    private Long tmTableId;

    @Column
    @Schema(description = "시간표명", example = "2024년 1학기 시간표")
    private String tmTableName;

    @Column
    @Schema(description = "카테고리", example = "학부")
    private String category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tmtable_id")
    @Schema(description = "강의 목록", example = """
        [
            {
                "courseId": 1,
                "courseName": "자바 프로그래밍",
                "professor": "홍길동",
                "room": "A101"
            }
        ]
        """)
    private List<CourseEntity> courses;
}

