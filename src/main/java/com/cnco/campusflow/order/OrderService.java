package com.cnco.campusflow.order;

import com.cnco.campusflow.menu.MenuEntity;
import com.cnco.campusflow.menu.MenuRepository;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final ConsumerRepository consumerRepository;
    private final OrderAddressRepository orderAddressRepository;
    private final AppUserRepository appUserRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;

    public ConsumerResponseDto addConsumerInfo(AppUserEntity appUser, ConsumerRequestDto consumerRequestDto) {
        ConsumerEntity consumerEntity = consumerRequestDto.getConsumerId() != null
                ? consumerRepository.findById(consumerRequestDto.getConsumerId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 consumerId: " + consumerRequestDto.getConsumerId()))
                : new ConsumerEntity();

        consumerEntity.setAppUser(appUser);
        consumerEntity.setDeliveryDemand(consumerRequestDto.getDeliveryDemand());
        consumerEntity.setStoreDemand(consumerRequestDto.getStoreDemand());
        consumerEntity.setOrderAddress(orderAddressRepository.findById(consumerRequestDto.getOrderAddrId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주소 ID: " + consumerRequestDto.getOrderAddrId())));

        consumerEntity = consumerRepository.save(consumerEntity);
        return toConsumerResponseDto(consumerEntity, appUser);
    }
    public ConsumerResponseDto getConsumerInfo(AppUserEntity appUser) {
        ConsumerEntity consumerEntity =  consumerRepository.findByAppUserAppUserId(appUser.getAppUserId());
        if(consumerEntity == null) {
            consumerEntity = new ConsumerEntity();
            consumerEntity.setAppUser(appUser);
        }

        return toConsumerResponseDto(consumerEntity, appUser);
    }

    public ConsumerAddressDto addConsumerAddress(AppUserEntity appUser, ConsumerAddressDto consumerAddressDto) {
        OrderAddressEntity addressEntity = consumerAddressDto.getOrderAddrId() != null
                ? orderAddressRepository.findById(consumerAddressDto.getOrderAddrId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주소 ID: " + consumerAddressDto.getOrderAddrId()))
                : new OrderAddressEntity();
        if(consumerAddressDto.getDefaultYn().equals("Y")) {
            for(OrderAddressEntity orderAddressEntity : orderAddressRepository.findAllByAppUserAppUserId(appUser.getAppUserId())) {
                orderAddressEntity.setDefaultYn("N");
                orderAddressRepository.save(orderAddressEntity);
            }
        }

        addressEntity.setAddressMain(consumerAddressDto.getAddressMain());
        addressEntity.setAddressDtl(consumerAddressDto.getAddressDtl());
        addressEntity.setAppUser(appUser);
        addressEntity.setDefaultYn(consumerAddressDto.getDefaultYn());

        addressEntity = orderAddressRepository.save(addressEntity);

        ConsumerEntity consumer = consumerRepository.findByAppUserAppUserId(appUser.getAppUserId());
        if(consumer == null) {
            consumer = new ConsumerEntity();
            AppUserEntity persistentUser = appUserRepository.findById(appUser.getAppUserId())
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            consumer.setAppUser(persistentUser);
        }
        consumer.setOrderAddress(addressEntity);
        consumerRepository.save(consumer);

        return toConsumerAddressDto(addressEntity);
    }
    public List<ConsumerAddressDto> getConsumerAddress(AppUserEntity appUser) {
        List<ConsumerAddressDto>consumerAddressDtos = new ArrayList<>();
        for(OrderAddressEntity orderAddressEntity : orderAddressRepository.findAllByAppUserAppUserId(appUser.getAppUserId())) {
            consumerAddressDtos.add( toConsumerAddressDto(orderAddressEntity));
        }
        return consumerAddressDtos;
    }
    public void deleteConsumerAddress(Long orderAddressId) {
        ConsumerEntity consumerEntity =consumerRepository.findByOrderAddressOrderAddrId(orderAddressId);
        if(consumerEntity != null) {
            consumerEntity.setOrderAddress(null);
            consumerRepository.save(consumerEntity);
        }
        orderAddressRepository.deleteById(orderAddressId);
    }
    public OrderEntity addOrder(OrderRequestDto orderRequestDto) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderStatus("R");
        orderEntity.setConsumer(consumerRepository.findById(orderRequestDto.getConsumerId()).get());
        List<MenuEntity> menuEntities = new ArrayList<>();
        for(Long menuId : orderRequestDto.getMenuIds()){
            menuEntities.add(menuRepository.findById(menuId).get());
        }
        orderEntity.setStore(menuEntities.get(0).getStore());
        orderEntity.setMenus(menuEntities);
        orderEntity.setTotalPrice(orderRequestDto.getTotalPrice());
        return orderRepository.save(orderEntity);
    }
   /* public OrderResponseDto getOrderHistory(AppUserEntity appUser) {
        Long consumerId = appUser.getAppUserId(); // 또는 appUser.getConsumer().getId() 형식일 수도 있음
        List<OrderEntity> orders = orderRepository.findAllByConsumerAppUserId(consumerId);

        List<OrderDto> orderDtos = orders.stream().map(order -> {
            OrderDto dto = new OrderDto();
            dto.setOrderId(order.getOrderId());
            dto.setTotalPrice(order.getTotalPrice());
            dto.setOrderStatus(order.getOrderStatus());
            dto.setOrderTime(order.getCreatedAt().toString()); // BaseEntity 상속 가정
            dto.setMenuNames(
                    order.getMenus().stream()
                            .map(menu -> menu.getProduct().getProductNm())
                            .collect(Collectors.toList())
            );
            return dto;
        }).collect(Collectors.toList());

        OrderResponseDto response = new OrderResponseDto();
        response.setOrders(orderDtos);
        return response;
    }*/

    private ConsumerResponseDto toConsumerResponseDto(ConsumerEntity consumer, AppUserEntity user) {
        ConsumerResponseDto dto = new ConsumerResponseDto();
        dto.setConsumerId(consumer.getConsumerId());
        dto.setDeliveryDemand(consumer.getDeliveryDemand());
        dto.setStoreDemand(consumer.getStoreDemand());
        dto.setAddressMain(consumer.getOrderAddress().getAddressMain());
        dto.setAddressDtl(consumer.getOrderAddress().getAddressDtl());
        dto.setPhone(user.getPhone());
        return dto;
    }

    private ConsumerAddressDto toConsumerAddressDto(OrderAddressEntity entity) {
        ConsumerAddressDto dto = new ConsumerAddressDto();
        dto.setOrderAddrId(entity.getOrderAddrId());
        dto.setAddressMain(entity.getAddressMain());
        dto.setAddressDtl(entity.getAddressDtl());
        dto.setDefaultYn(entity.getDefaultYn());
        return dto;
    }
}
