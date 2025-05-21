package com.cnco.campusflow.terms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TermsService {
    private final TermsRepository termsRepository;

    public List<TermsEntity> getAllTerms() {
        return termsRepository.findAll();
    }

    //
//    public TermsEntity getTermsById(Long boardId) {
//        return termsRepository.findById(boardId)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 약관입니다."));
//    }
} 