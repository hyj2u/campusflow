package com.cnco.campusflow.news;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.timetable.CourseDto;
import com.cnco.campusflow.timetable.TimeTableDto;
import com.cnco.campusflow.timetable.TimeTableService;
import com.cnco.campusflow.user.AppUserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
        * 뉴스 등록 및 신고 기능을 제공합니다.
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

    @Operation(
        summary = "뉴스 등록",
        description = """
            새로운 뉴스를 등록합니다.
            
            * URL은 필수 입력 항목입니다.
            * 활성화 여부를 설정할 수 있습니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "뉴스 등록 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값")
    })
    @PostMapping
    public ResponseEntity<CommonResponse<?>> addNews(
            @Parameter(description = "뉴스 등록 정보") @RequestBody NewsRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(newsService.addNews(requestDto)));
    }

    @DeleteMapping("/{newsId}")
    @Operation(
        summary = "뉴스 삭제",
        description = "특정 뉴스(newsId)를 삭제합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "뉴스 삭제 성공"),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 뉴스")
    })
    public ResponseEntity<Void> deleteNews(@PathVariable Long newsId) {
        newsService.deleteNews(newsId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/report/{newsId}")
    @Operation(
        summary = "뉴스 수정요청 신고",
        description = "뉴스 수정 요청을 신고합니다. (newsId는 URL, 인증된 사용자에서 appUserId 추출, 나머지는 body로 입력)"
    )
    public ResponseEntity<CommonResponse<?>> updateReport(
            @PathVariable Long newsId,
            @AuthenticationPrincipal com.cnco.campusflow.user.AppUserEntity user,
            @RequestBody NewsReportRequestDto requestDto) {
        return ResponseEntity.ok(CommonResponse.of(newsService.reportNews(newsId, user.getAppUserId(), requestDto)));
    }

    @GetMapping("/{newsId}")
    @Operation(
        summary = "뉴스 단건 조회",
        description = "특정 뉴스(newsId) 게시글을 조회합니다. (신고내역 개수 reportCnt도 함께 반환)"
    )
    public ResponseEntity<NewsDetailResponseDto> getNews(@PathVariable Long newsId) {
        return ResponseEntity.ok(newsService.getNewsDetail(newsId));
    }
}
