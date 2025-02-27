package com.cnco.campusflow.timetable;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/time")
@RequiredArgsConstructor
public class TimeTableController {
    private final TimeTableService timeTableService;

    @PostMapping
    public ResponseEntity<CommonResponse<?>> registerTimeTable(@AuthenticationPrincipal AppUserEntity appUser,  @RequestBody TimeTableDto request)  {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(timeTableService.registerTimetable(request, appUser)));
    }
    @PostMapping("/course")
    public ResponseEntity<CommonResponse<?>> registerCourse( @RequestBody CourseDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(timeTableService.registerCourse(request)));
    }
    @PutMapping("/{tmTableId}")
    public ResponseEntity<CommonResponse<?>> changeName(@PathVariable Long tmTableId,  @RequestParam String name)  {
        timeTableService.changeTimeTableName(tmTableId, name);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<CommonResponse<?>> getTimeTableList(@AuthenticationPrincipal AppUserEntity appUser)  {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of( timeTableService.getTimeTableList(appUser)));
    }
    @GetMapping("/{tmTableId}")
    public ResponseEntity<CommonResponse<?>> getTimeTableDtl(@PathVariable Long tmTableId, @RequestParam(required = false) Long day)  {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of( timeTableService.getTimeTableDtl(tmTableId, day)));
    }
    @DeleteMapping("/{tmTableId}")
    public ResponseEntity<CommonResponse<?>> deleteTmtable(@PathVariable Long tmTableId)  {
        timeTableService.deleteTmTable(tmTableId);
        return ResponseEntity.noContent().build();

    }
    @GetMapping("/course/{courseId}")
    public ResponseEntity<CommonResponse<?>> getCourse(@PathVariable Long courseId)  {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of( timeTableService.getCourse(courseId)));
    }
    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<CommonResponse<?>> deleteCourse(@PathVariable Long courseId)  {
        timeTableService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();

    }



}
