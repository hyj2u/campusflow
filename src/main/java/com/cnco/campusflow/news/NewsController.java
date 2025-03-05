package com.cnco.campusflow.news;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.timetable.CourseDto;
import com.cnco.campusflow.timetable.TimeTableDto;
import com.cnco.campusflow.timetable.TimeTableService;
import com.cnco.campusflow.user.AppUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<CommonResponse<?>> getTodayNews()  {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of( newsService.getTodayNews() ));
    }

}
