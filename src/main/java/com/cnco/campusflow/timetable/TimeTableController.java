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

@RestController
@RequestMapping("/time")
@RequiredArgsConstructor
@Tag(name = "TimeTable", description = "TimeTable management APIs")
public class TimeTableController {
    private final TimeTableService timeTableService;

    @Operation(summary = "Register a new timetable", description = "Creates a new timetable for the authenticated user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Timetable registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<CommonResponse<?>> registerTimeTable(
        @Parameter(description = "Authenticated user details") @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(description = "Timetable details") @RequestBody TimeTableDto request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(timeTableService.registerTimetable(request, appUser)));
    }

    @Operation(summary = "Register a new course", description = "Creates a new course for the timetable.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Course registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/course")
    public ResponseEntity<CommonResponse<?>> registerCourse(
        @Parameter(description = "Course details") @RequestBody CourseDto request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(timeTableService.registerCourse(request)));
    }

    @Operation(summary = "Change timetable name", description = "Updates the name of an existing timetable.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Timetable name updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{tmTableId}")
    public ResponseEntity<CommonResponse<?>> changeName(
        @Parameter(description = "Timetable ID") @PathVariable Long tmTableId,
        @Parameter(description = "New name for the timetable") @RequestParam String name
    ) {
        timeTableService.changeTimeTableName(tmTableId, name);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get timetable list", description = "Retrieves the list of timetables for the authenticated user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Timetable list retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<CommonResponse<?>> getTimeTableList(
        @Parameter(description = "Authenticated user details") @AuthenticationPrincipal AppUserEntity appUser
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(timeTableService.getTimeTableList(appUser)));
    }

    @Operation(summary = "Get timetable details", description = "Retrieves the details of a specific timetable.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Timetable details retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/{tmTableId}")
    public ResponseEntity<CommonResponse<?>> getTimeTableDtl(
        @Parameter(description = "Timetable ID") @PathVariable Long tmTableId,
        @Parameter(description = "Day of the week") @RequestParam(required = false) String day
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(timeTableService.getTimeTableDtl(tmTableId, day)));
    }

    @Operation(summary = "Delete timetable", description = "Deletes a specific timetable.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Timetable deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @DeleteMapping("/{tmTableId}")
    public ResponseEntity<CommonResponse<?>> deleteTmtable(
        @Parameter(description = "Timetable ID") @PathVariable Long tmTableId
    ) {
        timeTableService.deleteTmTable(tmTableId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get course details", description = "Retrieves the details of a specific course.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course details retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/course/{courseId}")
    public ResponseEntity<CommonResponse<?>> getCourse(
        @Parameter(description = "Course ID") @PathVariable Long courseId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(timeTableService.getCourse(courseId)));
    }

    @Operation(summary = "Delete course", description = "Deletes a specific course.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Course deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<CommonResponse<?>> deleteCourse(
        @Parameter(description = "Course ID") @PathVariable Long courseId
    ) {
        timeTableService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }
}
