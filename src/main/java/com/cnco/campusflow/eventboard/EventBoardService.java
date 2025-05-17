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
        EventBoardEntity eventEntity = eventBoardRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 이벤트 ID 입니다."));
        eventEntity.incrementViewCount();
        return convertEntityToDto(eventEntity);
    }

    public PaginatedResponse<EventBoardResponseDto> getEvents(Pageable pageable) {
        Page<EventBoardEntity> eventPage = eventBoardRepository.findAllByOrderByInsertTimestampDesc(pageable);

        List<EventBoardResponseDto> eventDtos = eventPage.getContent().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                eventDtos,
                eventPage.getNumber(),
                eventPage.getSize(),
                eventPage.getTotalElements(),
                eventPage.getTotalPages()
        );
    }

    private EventBoardResponseDto convertEntityToDto(EventBoardEntity eventEntity) {
        EventBoardResponseDto dto = new EventBoardResponseDto();
        dto.setBoardId(eventEntity.getBoardId());
        dto.setTitle(eventEntity.getTitle());
        dto.setContent(eventEntity.getContent());
        dto.setStartDate(eventEntity.getStartDate());
        dto.setEndDate(eventEntity.getEndDate());
        dto.setViewCnt(eventEntity.getViewCnt());
        dto.setPushYn(eventEntity.getPushYn());
        dto.setTalkYn(eventEntity.getTalkYn());
        dto.setFullExpYn(eventEntity.getFullExpYn());
        dto.setBrandId(eventEntity.getBrandId());
        dto.setBoardType(eventEntity.getBoardType() != null ? eventEntity.getBoardType().getCodeCd() : null);
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

        if (eventEntity.getStoreMappings() != null) {
            List<Long> storeIds = eventEntity.getStoreMappings().stream()
                    .map(EventBoardMappEntity::getStoreId)
                    .collect(Collectors.toList());
            dto.setStoreIds(storeIds);
        }

        return dto;
    }
} 