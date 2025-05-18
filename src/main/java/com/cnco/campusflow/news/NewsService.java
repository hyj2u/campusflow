package com.cnco.campusflow.news;

import com.cnco.campusflow.code.CodeEntity;
import com.cnco.campusflow.code.CodeRepository;
import com.cnco.campusflow.timetable.*;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NewsService {

    private final NewsRepository newsRepository;

    public List<NewsEntity> getTodayNews() {
        return newsRepository.findAllByActiveYn("Y");
    }

    public NewsEntity addNews(NewsRequestDto requestDto) {
        NewsEntity news = new NewsEntity();
        news.setUrl(requestDto.getUrl());
        news.setActiveYn(requestDto.getActiveYn());
        news.setReportYn("N");  // 기본값 N
        news.setReportReason(null);  // 기본값 null

        return newsRepository.save(news);
    }

    public NewsEntity updateReportInfo(Long newsId, NewsReportRequestDto dto) {
        NewsEntity news = newsRepository.findById(newsId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 뉴스입니다."));
        news.setReportReason(dto.getReportReason());
        news.setReportYn(dto.getReportYn());
        return newsRepository.save(news);
    }

    public void deleteNews(Long newsId) {
        NewsEntity news = newsRepository.findById(newsId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 뉴스입니다."));
        newsRepository.delete(news);
    }
}
