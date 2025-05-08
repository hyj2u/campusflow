package com.cnco.campusflow.college;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.community.CommunityBoardRequestDto;
import com.cnco.campusflow.community.CommunityService;
import com.cnco.campusflow.community.ReplyRequestDto;
import com.cnco.campusflow.community.ReportDto;
import com.cnco.campusflow.user.AppUserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/college")
@RequiredArgsConstructor
@Tag(name = "College", description = "College management APIs")
public class CollegeController {
    private final CollegeService collegeService;

    @Operation(summary = "Get colleges", description = "Retrieves the list of colleges.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Colleges retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping
    public ResponseEntity<?> getColleges() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(collegeService.getColleges()));
    }


}
