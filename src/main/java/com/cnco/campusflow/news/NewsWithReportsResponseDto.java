package com.cnco.campusflow.news;

import lombok.Data;
import java.util.List;

@Data
public class NewsWithReportsResponseDto {
    private NewsEntity news;
    private List<NewsReportEntity> reports;
} 