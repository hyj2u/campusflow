package com.cnco.campusflow.oftenqna;

import com.cnco.campusflow.common.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oftenqna")
@RequiredArgsConstructor
public class OftenQnaController {

    private final OftenQnaService oftenQnaService;

    @PostMapping
    public ResponseEntity<OftenQnaResponseDto> createQna(@RequestBody OftenQnaRequestDto requestDto) {
        return ResponseEntity.ok(oftenQnaService.createQna(requestDto));
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<OftenQnaResponseDto>> getQnas(
            @RequestParam(required = false) String category,
            @PageableDefault(size = 10) Pageable pageable) {
        if (category != null) {
            return ResponseEntity.ok(oftenQnaService.getQnasByCategory(category, pageable));
        }
        return ResponseEntity.ok(oftenQnaService.getQnas(pageable));
    }

    @GetMapping("/{qnaId}")
    public ResponseEntity<OftenQnaResponseDto> getQna(@PathVariable Long qnaId) {
        return ResponseEntity.ok(oftenQnaService.getQna(qnaId));
    }
} 