package com.cnco.campusflow.college;

import com.cnco.campusflow.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "college", schema = "admin")
@Schema(
    name = "CollegeEntity",
    description = """
        대학 엔티티
        
        * 대학의 기본 정보를 저장합니다.
        * 대학 코드는 유니크합니다.
        """,
    example = """
        {
            "collegeId": 1,
            "collegeCode": "SNU",
            "collegeName": "서울대학교"
        }
        """
)
public class CollegeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "대학 ID", example = "1")
    private Integer collegeId;

    @Column(unique = true)
    @Schema(description = "대학 코드", example = "SNU")
    private String collegeCode;

    @Column
    @Schema(description = "대학명", example = "서울대학교")
    private String collegeName;
}
