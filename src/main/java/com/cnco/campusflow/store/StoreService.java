package com.cnco.campusflow.store;

import com.cnco.campusflow.user.AppUserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;
    @Value("${image.base.path}")
    private String imageBasePath;
    @Value("${image.base.url}")
    private String imageBaseUrl;
    private final FavoriteStoreRepository favoriteStoreRepository;

    public Page<StoreListDto> findStores(String search, Double latitude, Double longitude, Pageable pageable) {
        return storeRepository.findStores(search, latitude, longitude, pageable);
    }

    public Page<StoreListDto> getMyStores(AppUserEntity appUser, Pageable pageable) {
        // 즐겨찾기한 매장 조회 (FavoriteStoreEntity 리스트)
        Page<FavoriteStoreEntity> storeEntities = favoriteStoreRepository.findFavoriteStoreEntitiesByUserAppUserId(appUser.getAppUserId(), pageable);

        // FavoriteStore -> StoreListDto 변환
        return storeEntities.map(favorite -> convertToDto(favorite.getStore()));
    }
    public StoreResponseDto getStoreDetail(Long storeId) {
        StoreEntity store= storeRepository.findById(storeId).orElseThrow(EntityNotFoundException::new);
        StoreResponseDto storeResponseDto = new StoreResponseDto();
        storeResponseDto.setStorePasswd(store.getStorePasswd());
        storeResponseDto.setStoreId(storeId);
        storeResponseDto.setStoreStatus(store.getStoreStatus());
        storeResponseDto.setAddressDtl(store.getAddressDtl());
        storeResponseDto.setStoreNm(store.getStoreNm());
        storeResponseDto.setAddressMain(store.getAddressMain());
        storeResponseDto.setOwner(store.getOwner());
        storeResponseDto.setDayCloseTm(store.getDayCloseTm());
        storeResponseDto.setDayOpenTm(store.getDayOpenTm());
        storeResponseDto.setDeliveryYn(store.getDeliveryYn());
        storeResponseDto.setInhereYn(store.getInhereYn());
        storeResponseDto.setSatCloseTm(store.getSatCloseTm());
        storeResponseDto.setSatOpenTm(store.getSatOpenTm());
        storeResponseDto.setSunCloseTm(store.getSunCloseTm());
        storeResponseDto.setSunOpenTm(store.getSunOpenTm());
        storeResponseDto.setTogoYn(store.getTogoYn());
        if(store.getMainImg() != null) {
            storeResponseDto.setMainImgUrl(imageBaseUrl+"/"+store.getMainImg().getImageId());
        }
        return storeResponseDto;
    }

    public StoreEntity addFavoriteStore(AppUserEntity appUser, Long storeId) {
        FavoriteStoreEntity favoriteStoreEntity = new FavoriteStoreEntity();
        favoriteStoreEntity.setUser(appUser);
        StoreEntity storeEntity = storeRepository.findById(storeId).get();
        favoriteStoreEntity.setStore(storeEntity);
        favoriteStoreRepository.save(favoriteStoreEntity);
        return storeEntity;
    }

    public void removeFavoriteStore(AppUserEntity appUser, Long storeId) {
        boolean exists = favoriteStoreRepository.existsByUserAppUserIdAndStoreStoreId(
                appUser.getAppUserId(), storeId);
        if (!exists) {
            throw new EntityNotFoundException("즐겨찾는 가게가 존재하지 않습니다.");
        }
        favoriteStoreRepository.deleteByUserAppUserIdAndStoreStoreId(
                appUser.getAppUserId(), storeId
        );
    }

    private StoreListDto convertToDto(StoreEntity store) {
        StoreListDto dto = new StoreListDto();
        dto.setStoreId(store.getStoreId());
        dto.setStoreNm(store.getStoreNm());
        dto.setOwner(store.getOwner());
        dto.setAddressMain(store.getAddressMain());
        dto.setAddressDtl(store.getAddressDtl());
        dto.setDayOpenTm(store.getDayOpenTm());
        dto.setDayCloseTm(store.getDayCloseTm());
        dto.setSatOpenTm(store.getSatOpenTm());
        dto.setSatCloseTm(store.getSatCloseTm());
        dto.setSunOpenTm(store.getSunOpenTm());
        dto.setSunCloseTm(store.getSunCloseTm());
        dto.setDeliveryYn(store.getDeliveryYn());
        dto.setTogoYn(store.getTogoYn());
        dto.setInhereYn(store.getInhereYn());
        return dto;
    }


}
