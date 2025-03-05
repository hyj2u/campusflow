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

}
