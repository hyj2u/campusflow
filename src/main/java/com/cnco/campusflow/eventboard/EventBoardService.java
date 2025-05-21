package com.cnco.campusflow.eventboard;

import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.image.ImageEntity;
import com.cnco.campusflow.image.ImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EventBoardService {

    private final EventBoardRepository eventBoardRepository;
    
    @Value("${image.base.url}")
    private String imageBaseUrl;

    public EventBoardResponseDto getEvent(Long eventId) {
        EventBoardEntity entity = eventBoardRepository.findById(eventId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이벤트입니다."));
        EventBoardResponseDto dto = convertEntityToDto(entity);
        LocalDate today = LocalDate.now();
        if (entity.getEndDate() != null && entity.getEndDate().isBefore(today)) {
            dto.setEndDateCheck("Y");
        } else {
            dto.setEndDateCheck("N");
        }
        return dto;
    }

    public PaginatedResponse<EventBoardResponseDto> getEvents(Pageable pageable, Long storeId, String checkEndDate) {
        List<EventBoardEntity> eventEntities;
        if (storeId != null) {
            eventEntities = eventBoardRepository.findByStoreMappings_StoreId(storeId);
        } else {
            eventEntities = eventBoardRepository.findAll();
        }
        List<EventBoardResponseDto> filtered = eventEntities.stream()
            .map(this::convertEntityToDto)
            .peek(dto -> {
                LocalDate today = LocalDate.now();
                if (dto.getEndDate() != null && dto.getEndDate().isBefore(today)) {
                    dto.setEndDateCheck("Y");
                } else {
                    dto.setEndDateCheck("N");
                }
            })
            .filter(dto -> {
                if (checkEndDate == null || checkEndDate.isEmpty()) return true;
                return checkEndDate.equalsIgnoreCase(dto.getEndDateCheck());
            })
            .collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filtered.size());
        List<EventBoardResponseDto> pageContent = (start > end) ? new ArrayList<>() : filtered.subList(start, end);
        return new PaginatedResponse<>(
            pageContent,
            pageable.getPageNumber(),
            pageable.getPageSize(),
            filtered.size(),
            (int) Math.ceil((double) filtered.size() / pageable.getPageSize())
        );
    }

    private EventBoardResponseDto convertEntityToDto(EventBoardEntity eventEntity) {
        EventBoardResponseDto dto = new EventBoardResponseDto();
        dto.setBoardId(eventEntity.getBoardId());
        dto.setStartDate(eventEntity.getStartDate());
        dto.setEndDate(eventEntity.getEndDate());
        dto.setViewCnt(eventEntity.getViewCnt());
        dto.setPushYn(eventEntity.getPushYn());
        dto.setTalkYn(eventEntity.getTalkYn());
        dto.setFullExpYn(eventEntity.getFullExpYn());
        dto.setBrandId(eventEntity.getBrandId());
        dto.setBoardType(eventEntity.getBoardType() != null ? eventEntity.getBoardType().getCodeCd() : null);
        dto.setBoardTypeCodeCd(eventEntity.getBoardType() != null ? eventEntity.getBoardType().getCodeCd() : null);
        dto.setBoardTypeCodeId(eventEntity.getBoardType() != null ? eventEntity.getBoardType().getCodeId() : null);
        dto.setNickname(eventEntity.getAppUser().getNickname());
        dto.setAppUserId(eventEntity.getAppUser().getAppUserId());

        List<ImageResponseDto> imageDtos = new ArrayList<>();
        if (eventEntity.getImages() != null) {
            for (ImageEntity image : eventEntity.getImages()) {
                ImageResponseDto imageDto = new ImageResponseDto();
                imageDto.setImageId(image.getImageId());
                imageDto.setImageUrl(imageBaseUrl + "/" + image.getImageId());
                imageDtos.add(imageDto);
            }
            dto.setImages(imageDtos);
        }

        if (eventEntity.getAppUser().getProfileImg() != null) {
            dto.setProfileImgUrl(imageBaseUrl + "/" + eventEntity.getAppUser().getProfileImg().getImageId());
        }

        return dto;
    }
} 