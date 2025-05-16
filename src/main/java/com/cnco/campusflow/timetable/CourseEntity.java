package com.cnco.campusflow.timetable;

import com.cnco.campusflow.code.CodeEntity;
import com.cnco.campusflow.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "course", schema = "admin")
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "CourseEntity",
    description = """
        강의 엔티티
        
        * 강의의 기본 정보를 저장합니다.
        * 강의명, 시간, 장소, 교수, 색상, 요일 정보를 관리합니다.
        * 생성/수정 시간이 자동으로 기록됩니다.
        """,
    example = """
        {
            "courseId": 1,
            "courseName": "자바 프로그래밍",
            "startTime": "09:00",
            "endTime": "10:30",
            "location": "A101",
            "professor": "홍길동",
            "color": "#FF0000",
            "days": [ "MON", "WED" ]
        }
        """
)
public class CourseEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "강의 번호", example = "1")
    private Long courseId;

    @Column
    @Schema(description = "강의명", example = "자바 프로그래밍")
    private String courseName;

    @Column
    @Schema(description = "시작 시간", example = "09:00")
    private String startTime;

    @Column
    @Schema(description = "종료 시간", example = "10:30")
    private String endTime;

    @Column
    @Schema(description = "강의실", example = "A101")
    private String location;

    @Column
    @Schema(description = "교수명", example = "홍길동")
    private String professor;

    @Column
    @Schema(description = "색상 코드", example = "#FF0000")
    private String color;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_day_mapp",                      // 조인 테이블 이름
            joinColumns = @JoinColumn(name = "course_id"),  // LectureEntity를 참조하는 외래키
            inverseJoinColumns = @JoinColumn(name = "code_id") // CodeEntity를 참조하는 외래키
    )
    @Schema(
        description = """
            강의 요일 목록
            
            * MON: 월요일
            * TUE: 화요일
            * WED: 수요일
            * THU: 목요일
            * FRI: 금요일
            """,
        example = """
            [ "MON", "WED" ]
            ]
            """,
        allowableValues = {"MON", "TUE", "WED", "THU", "FRI"}
    )
    private List<CodeEntity> days;
}

