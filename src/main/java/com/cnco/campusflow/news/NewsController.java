package com.cnco.campusflow.news;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.timetable.CourseDto;
import com.cnco.campusflow.timetable.TimeTableDto;
import com.cnco.campusflow.timetable.TimeTableService;
import com.cnco.campusflow.user.AppUserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@Tag(name = "News", description = "News management APIs")
public class NewsController {
    private final NewsService newsService;

    @Operation(summary = "Get today's news", description = "Retrieves the news for today.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Today's news retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping
    public ResponseEntity<CommonResponse<?>> getTodayNews() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(newsService.getTodayNews()));
    }
}
