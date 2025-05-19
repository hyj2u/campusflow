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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsReportRepository newsReportRepository;

    public List<NewsDetailResponseDto> getTodayNews() {
        List<NewsEntity> list = newsRepository.findAllByActiveYn("Y");
        List<NewsDetailResponseDto> result = new ArrayList<>();
        for (NewsEntity news : list) {
            if (news.getViewCnt() == null) news.setViewCnt(0);
            int reportCnt = newsReportRepository.findAllByNews(news).size();
            result.add(new NewsDetailResponseDto(news, reportCnt));
        }
        return result;
    }

    public List<Map<String, Object>> getTodayNewsFlat() {
        List<NewsEntity> list = newsRepository.findAllByActiveYn("Y");
        List<Map<String, Object>> result = new ArrayList<>();
        for (NewsEntity news : list) {
            if (news.getViewCnt() == null) news.setViewCnt(0);
            int reportCnt = newsReportRepository.findAllByNews(news).size();
            Map<String, Object> map = new HashMap<>();
            map.put("insertTimestamp", news.getInsertTimestamp());
            map.put("updateTimestamp", news.getUpdateTimestamp());
            map.put("newsId", news.getNewsId());
            map.put("url", news.getUrl());
            map.put("activeYn", news.getActiveYn());
            map.put("viewCnt", news.getViewCnt());
            map.put("reportCnt", reportCnt);
            result.add(map);
        }
        return result;
    }

    public NewsEntity addNews(NewsRequestDto requestDto) {
        NewsEntity news = new NewsEntity();
        news.setUrl(requestDto.getUrl());
        news.setActiveYn(requestDto.getActiveYn());
        news.setViewCnt(0);
        return newsRepository.save(news);
    }

    public NewsReportEntity reportNews(Long newsId, Long appUserId, NewsReportRequestDto requestDto) {
        NewsEntity news = newsRepository.findById(newsId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 뉴스입니다."));
        NewsReportEntity report = new NewsReportEntity();
        report.setNews(news);
        report.setReportReason(requestDto.getReportReason());
        report.setComment(requestDto.getComment());
        report.setAppUserId(appUserId);
        report.setViewCnt(0);
        return newsReportRepository.save(report);
    }

    public void deleteNews(Long newsId) {
        NewsEntity news = newsRepository.findById(newsId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 뉴스입니다."));
        newsRepository.delete(news);
    }

    public NewsEntity getNews(Long newsId) {
        NewsEntity news = newsRepository.findById(newsId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 뉴스입니다."));
        if (news.getViewCnt() == null) news.setViewCnt(0);
        news.setViewCnt(news.getViewCnt() + 1);
        newsRepository.save(news);
        return news;
    }

    public NewsDetailResponseDto getNewsDetail(Long newsId) {
        NewsEntity news = getNews(newsId);
        int reportCnt = newsReportRepository.findAllByNews(news).size();
        NewsDetailResponseDto dto = new NewsDetailResponseDto();
        dto.setNews(news);
        dto.setReportCnt(reportCnt);
        return dto;
    }

    public Map<String, Object> getNewsDetailFlat(Long newsId) {
        NewsEntity news = getNews(newsId);
        int reportCnt = newsReportRepository.findAllByNews(news).size();
        Map<String, Object> map = new HashMap<>();
        map.put("insertTimestamp", news.getInsertTimestamp());
        map.put("updateTimestamp", news.getUpdateTimestamp());
        map.put("newsId", news.getNewsId());
        map.put("url", news.getUrl());
        map.put("activeYn", news.getActiveYn());
        map.put("viewCnt", news.getViewCnt());
        map.put("reportCnt", reportCnt);
        return map;
    }

    public Map<String, Object> reportNewsAndReturnFlat(Long newsId, Long appUserId, NewsReportRequestDto requestDto) {
        reportNews(newsId, appUserId, requestDto);
        return getNewsDetailFlat(newsId);
    }
}
