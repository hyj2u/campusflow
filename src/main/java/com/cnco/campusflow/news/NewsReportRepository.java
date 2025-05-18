package com.cnco.campusflow.news;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsReportRepository extends JpaRepository<NewsReportEntity, Long> {
    List<NewsReportEntity> findAllByNews(NewsEntity news);
} 