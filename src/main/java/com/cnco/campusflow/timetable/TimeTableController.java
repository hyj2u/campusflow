package com.cnco.campusflow.timetable;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.user.*;
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
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/time")
@RequiredArgsConstructor
@Tag(
    name = "TimeTable",
    description = """
        시간표 관리 API
        
        * 시간표 생성, 조회, 수정, 삭제 기능을 제공합니다.
        * 강의 등록, 조회, 삭제 기능을 제공합니다.
        * JWT 인증이 필요한 API가 포함되어 있습니다.
        """
)
public class TimeTableController {
    private final TimeTableService timeTableService;

    @Operation(
        summary = "시간표 등록",
        description = """
            새로운 시간표를 등록합니다.
            
            * 인증된 사용자의 시간표를 생성합니다.
            * 시간표명과 카테고리 정보가 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "시간표 등록 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값")
    })
    @PostMapping
    public ResponseEntity<CommonResponse<?>> registerTimeTable(
        @Parameter(description = "인증된 사용자 정보") @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(description = "시간표 정보") @RequestBody TimeTableDto request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(timeTableService.registerTimetable(request, appUser)));
    }

    @Operation(
        summary = "강의 등록",
        description = """
            새로운 강의를 등록합니다.
            
            * 시간표에 강의를 추가합니다.
            * 강의명, 요일, 장소, 시간, 교수, 색상 정보가 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "강의 등록 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값")
    })
    @PostMapping("/course")
    public ResponseEntity<CommonResponse<?>> registerCourse(
        @Parameter(description = "강의 정보") @RequestBody CourseDto request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(timeTableService.registerCourse(request)));
    }

    @Operation(
        summary = "시간표명 변경",
        description = """
            기존 시간표의 이름을 변경합니다.
            
            * 시간표 ID와 새로운 이름이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "시간표명 변경 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값")
    })
    @PutMapping("/{tmTableId}")
    public ResponseEntity<CommonResponse<?>> changeName(
        @Parameter(description = "시간표 번호") @PathVariable Long tmTableId,
        @Parameter(description = "새로운 시간표명") @RequestParam String name
    ) {
        timeTableService.changeTimeTableName(tmTableId, name);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "시간표 목록 조회",
        description = """
            인증된 사용자의 시간표 목록을 조회합니다.
            
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "시간표 목록 조회 성공"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @GetMapping
    public ResponseEntity<CommonResponse<?>> getTimeTableList(
        @Parameter(description = "인증된 사용자 정보") @AuthenticationPrincipal AppUserEntity appUser
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(timeTableService.getTimeTableList(appUser)));
    }

    @Operation(
        summary = "시간표 상세 조회",
        description = """
            특정 시간표의 상세 정보를 조회합니다.
            
            * 시간표 ID가 필요합니다.
            * 요일별 필터링이 가능합니다.
            * day 파라미터는 선택사항이며, 다음 값만 사용 가능합니다:
              - MON: 월요일
              - TUE: 화요일
              - WED: 수요일
              - THU: 목요일
              - FRI: 금요일
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "시간표 상세 조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값")
    })
    @GetMapping("/{tmTableId}")
    public ResponseEntity<CommonResponse<?>> getTimeTableDtl(
        @Parameter(
            description = "시간표 번호",
            example = "1"
        ) 
        @PathVariable Long tmTableId,
        @Parameter(
            description = """
                요일 (선택사항)
                
                * MON: 월요일
                * TUE: 화요일
                * WED: 수요일
                * THU: 목요일
                * FRI: 금요일
                """,
            example = "MON",
            schema = @Schema(
                allowableValues = {"MON", "TUE", "WED", "THU", "FRI"}
            )
        ) 
        @RequestParam(required = false) String day
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(timeTableService.getTimeTableDtl(tmTableId, day)));
    }

    @Operation(
        summary = "시간표 삭제",
        description = """
            특정 시간표를 삭제합니다.
            
            * 시간표 ID가 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "시간표 삭제 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값")
    })
    @DeleteMapping("/{tmTableId}")
    public ResponseEntity<CommonResponse<?>> deleteTmtable(
        @Parameter(description = "시간표 번호") @PathVariable Long tmTableId
    ) {
        timeTableService.deleteTmTable(tmTableId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "강의 상세 조회",
        description = """
            특정 강의의 상세 정보를 조회합니다.
            
            * 강의 ID가 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "강의 상세 조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값")
    })
    @GetMapping("/course/{courseId}")
    public ResponseEntity<CommonResponse<?>> getCourse(
        @Parameter(description = "강의 번호") @PathVariable Long courseId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(timeTableService.getCourse(courseId)));
    }

    @Operation(
        summary = "강의 삭제",
        description = """
            특정 강의를 삭제합니다.
            
            * 강의 ID가 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "강의 삭제 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값")
    })
    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<CommonResponse<?>> deleteCourse(
        @Parameter(description = "강의 번호") @PathVariable Long courseId
    ) {
        timeTableService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }
}
