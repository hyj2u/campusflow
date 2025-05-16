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
