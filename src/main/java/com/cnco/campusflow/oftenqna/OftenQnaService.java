package com.cnco.campusflow.oftenqna;

import com.cnco.campusflow.common.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OftenQnaService {

    private final OftenQnaRepository oftenQnaRepository;

    public OftenQnaResponseDto createQna(OftenQnaRequestDto requestDto) {
        OftenQnaEntity qnaEntity = new OftenQnaEntity();
        qnaEntity.setTitle(requestDto.getTitle());
        qnaEntity.setContent(requestDto.getContent());
        qnaEntity.setCategory(requestDto.getCategory());
        qnaEntity.setViewCnt(0);

        OftenQnaEntity savedEntity = oftenQnaRepository.save(qnaEntity);
        return convertEntityToDto(savedEntity);
    }

    public OftenQnaResponseDto getQna(Long qnaId) {
        OftenQnaEntity qnaEntity = oftenQnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 FAQ ID 입니다."));
        
        qnaEntity.incrementViewCount();
        return convertEntityToDto(qnaEntity);
    }

    public PaginatedResponse<OftenQnaResponseDto> getQnas(Pageable pageable) {
        Page<OftenQnaEntity> qnaPage = oftenQnaRepository.findAllOrderByInsertTimestampDesc(pageable);

        List<OftenQnaResponseDto> qnaDtos = qnaPage.getContent().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                qnaDtos,
                qnaPage.getNumber(),
                qnaPage.getSize(),
                qnaPage.getTotalElements(),
                qnaPage.getTotalPages()
        );
    }

    public PaginatedResponse<OftenQnaResponseDto> getQnasByCategory(String category, Pageable pageable) {
        Page<OftenQnaEntity> qnaPage = oftenQnaRepository.findByCategoryOrderByInsertTimestampDesc(category, pageable);

        List<OftenQnaResponseDto> qnaDtos = qnaPage.getContent().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                qnaDtos,
                qnaPage.getNumber(),
                qnaPage.getSize(),
                qnaPage.getTotalElements(),
                qnaPage.getTotalPages()
        );
    }

    private OftenQnaResponseDto convertEntityToDto(OftenQnaEntity qnaEntity) {
        OftenQnaResponseDto dto = new OftenQnaResponseDto();
        dto.setBoardId(qnaEntity.getBoardId());
        dto.setTitle(qnaEntity.getTitle());
        dto.setContent(qnaEntity.getContent());
        dto.setViewCnt(qnaEntity.getViewCnt());
        dto.setCategory(qnaEntity.getCategory());
        return dto;
    }
} 