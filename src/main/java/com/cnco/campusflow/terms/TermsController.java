package com.cnco.campusflow.terms;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.cnco.campusflow.common.CommonResponse;

@RestController
@RequestMapping("/terms")
@RequiredArgsConstructor
@Tag(name = "서비스 약관", description = "서비스 약관(terms) API")
public class TermsController {
    private final TermsService termsService;

    @GetMapping
    @Operation(summary = "전체 약관 목록 조회", description = "terms 테이블의 전체 레코드 내용을 조회합니다.")
    public ResponseEntity<CommonResponse<List<TermsEntity>>> getAllTerms() {
        return ResponseEntity.ok(CommonResponse.of(termsService.getAllTerms()));
    }

//    @GetMapping("/{boardId}")
//    @Operation(summary = "약관 단건 조회", description = "boardId로 약관을 조회합니다.")
//    public ResponseEntity<TermsEntity> getTermsById(
//            @Parameter(description = "약관 ID", example = "1")
//            @PathVariable Long boardId) {
//        return ResponseEntity.ok(termsService.getTermsById(boardId));
//    }
} 