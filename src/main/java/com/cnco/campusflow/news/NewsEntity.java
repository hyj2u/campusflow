package com.cnco.campusflow.news;

import com.cnco.campusflow.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "today_news", schema = "admin")
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "NewsEntity",
    description = """
        뉴스 엔티티
        
        * 오늘의 뉴스 정보를 저장합니다.
        * 뉴스 URL과 활성화 여부를 관리합니다.
        * 생성/수정 시간이 자동으로 기록됩니다.
        * 신고 기능을 지원합니다.
        """,
    example = """
        {
            "newsId": 1,
            "url": "https://example.com/news/1",
            "activeYn": "Y",
            "reportYn": "N",
            "reportReason": "부적절한 내용"
        }
        """
)
public class NewsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Schema(description = "뉴스 번호", example = "1")
    private Long newsId;

    @Column
    @Schema(description = "뉴스 URL", example = "https://example.com/news/1")
    private String url;

    @Column
    @Schema(description = "활성화 여부", example = "Y", allowableValues = {"Y", "N"})
    private String activeYn;

    @Column
    @Schema(description = "조회수", example = "0")
    private Integer viewCnt;
}

