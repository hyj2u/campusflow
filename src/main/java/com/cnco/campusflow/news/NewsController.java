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
@Tag(
    name = "News",
    description = """
        뉴스 관리 API
        
        * 오늘의 뉴스 정보를 조회하는 기능을 제공합니다.
        * 활성화된 뉴스 URL을 반환합니다.
        """
)
public class NewsController {
    private final NewsService newsService;

    @Operation(
        summary = "오늘의 뉴스 조회",
        description = """
            오늘의 뉴스 정보를 조회합니다.
            
            * 활성화된 뉴스 URL을 반환합니다.
            * 뉴스 정보는 관리자 페이지에서 관리됩니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "뉴스 조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값")
    })
    @GetMapping
    public ResponseEntity<CommonResponse<?>> getTodayNews() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(newsService.getTodayNews()));
    }
}
