package com.cnco.campusflow.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginatedResponse<T> {
    private List<T> data;    // 데이터 목록
    private int page;           // 현재 페이지 번호
    private int size;           // 페이지 크기
    private long totalElements; // 전체 요소 수
    private int totalPages;     // 전체 페이지 수
}
