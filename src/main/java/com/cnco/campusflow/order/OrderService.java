package com.cnco.campusflow.order;

import com.cnco.campusflow.menu.MenuEntity;
import com.cnco.campusflow.menu.MenuOptionDto;
import com.cnco.campusflow.menu.MenuRepository;
import com.cnco.campusflow.menu.MenuResponseDto;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.user.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        ConsumerEntity consumerEntity;
        if(consumerRepository.findById(consumerRequestDto.getConsumerId()).isPresent()) {
                consumerEntity = consumerRepository.findById(consumerRequestDto.getConsumerId()).get();
        }else{
            consumerEntity = new ConsumerEntity();
            consumerEntity.setAppUser(appUser);
        }
        consumerEntity.setDeliveryDemand(consumerRequestDto.getDeliveryDemand());
        consumerEntity.setStoreDemand(consumerRequestDto.getStoreDemand());
        consumerEntity.setOrderAddress(orderAddressRepository.findById(consumerRequestDto.getOrderAddrId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주소 ID: " + consumerRequestDto.getOrderAddrId())));

        consumerEntity = consumerRepository.save(consumerEntity);
        return toConsumerResponseDto(consumerEntity, appUser);
    }

    public ConsumerResponseDto getConsumerInfo(AppUserEntity appUser) {
        ConsumerEntity consumerEntity = consumerRepository.findByAppUserAppUserId(appUser.getAppUserId());
        if (consumerEntity == null) {
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
        if (consumerAddressDto.getDefaultYn().equals("Y")) {
            for (OrderAddressEntity orderAddressEntity : orderAddressRepository.findAllByAppUserAppUserId(appUser.getAppUserId())) {
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
        if (consumer == null) {
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
        List<ConsumerAddressDto> consumerAddressDtos = new ArrayList<>();
        for (OrderAddressEntity orderAddressEntity : orderAddressRepository.findAllByAppUserAppUserId(appUser.getAppUserId())) {
            consumerAddressDtos.add(toConsumerAddressDto(orderAddressEntity));
        }
        return consumerAddressDtos;
    }

    public void deleteConsumerAddress(Long orderAddressId) {
        ConsumerEntity consumerEntity = consumerRepository.findByOrderAddressOrderAddrId(orderAddressId);
        if (consumerEntity != null) {
            consumerEntity.setOrderAddress(null);
            consumerRepository.save(consumerEntity);
        }
        if (orderAddressRepository.existsById(orderAddressId)) {
            orderAddressRepository.deleteById(orderAddressId);
        } else {
            throw new EntityNotFoundException("OrderAddress not found with id: " + orderAddressId);
        }
        orderAddressRepository.deleteById(orderAddressId);
    }

    public OrderEntity addOrder(OrderRequestDto orderRequestDto) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderStatus("R");
        orderEntity.setConsumer(consumerRepository.findById(orderRequestDto.getConsumerId()).get());
        List<MenuEntity> menuEntities = new ArrayList<>();
        for (Long menuId : orderRequestDto.getMenuIds()) {
            menuEntities.add(menuRepository.findById(menuId).get());
        }
        orderEntity.setMenus(menuEntities);
        orderEntity.setTotalPrice(orderRequestDto.getTotalPrice());
        return orderRepository.save(orderEntity);
    }

    public OrderResponseDto getOrder(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. ID: " + orderId));
        return toOrderResponseDto(order);

    }

    public List<OrderResponseDto> getOrderHistory(AppUserEntity appUser) {

        List<OrderEntity> orders = orderRepository.findTop10ByConsumerAppUserAppUserIdOrderByOrderIdDesc(appUser.getAppUserId());
        return orders.stream()
                .map(this::toOrderResponseDto)
                .collect(Collectors.toList());
    }

    private ConsumerResponseDto toConsumerResponseDto(ConsumerEntity consumer, AppUserEntity user) {
        ConsumerResponseDto dto = new ConsumerResponseDto();
        dto.setConsumerId(consumer.getConsumerId());
        dto.setDeliveryDemand(consumer.getDeliveryDemand());
        dto.setStoreDemand(consumer.getStoreDemand());
        dto.setAddressMain(consumer.getOrderAddress().getAddressMain());
        dto.setAddressDtl(consumer.getOrderAddress().getAddressDtl());
        dto.setPhone(user.getPhone());
        dto.setDefaultYn(consumer.getOrderAddress().getDefaultYn());
        dto.setOrderAddrId(consumer.getOrderAddress().getOrderAddrId());
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

    private OrderResponseDto toOrderResponseDto(OrderEntity order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(order.getOrderId());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setOrderStatus(order.getOrderStatus());


        ConsumerResponseDto consumerDto = new ConsumerResponseDto();
        consumerDto.setConsumerId(order.getConsumer().getConsumerId());
        consumerDto.setPhone(order.getConsumer().getAppUser().getPhone());
        consumerDto.setOrderAddrId(order.getConsumer().getOrderAddress().getOrderAddrId());
        consumerDto.setAddressMain(order.getConsumer().getOrderAddress().getAddressMain());
        consumerDto.setAddressDtl(order.getConsumer().getOrderAddress().getAddressDtl());
        consumerDto.setDeliveryDemand(order.getConsumer().getDeliveryDemand());
        consumerDto.setStoreDemand(order.getConsumer().getStoreDemand());
        dto.setConsumer(consumerDto);

        List<MenuResponseDto> menuDtos = order.getMenus().stream().map(menu -> {
            MenuResponseDto menuDto = new MenuResponseDto();
            menuDto.setMenuId(menu.getMenuId());
            menuDto.setProductId(menu.getProduct().getProductId());
            menuDto.setProductName(menu.getProduct().getProductNm());
            menuDto.setStoreId(menu.getStore().getStoreId());
            menuDto.setStoreName(menu.getStore().getStoreNm());
            menuDto.setOrderCnt(menu.getOrderCnt());
            List<MenuOptionDto> optionDtos = menu.getOptions().stream().map(option -> {
                MenuOptionDto optDto = new MenuOptionDto();
                optDto.setOptionId(option.getOptionEntity().getOptionId());
                optDto.setOptionNm(option.getOptionEntity().getOptionNm());
                optDto.setChosenNum(option.getChosenNum());
                optDto.setTotalPrice(option.getTotalPrice());
                optDto.setOptDtlId(option.getOptDtlEntity().getOptDtlId());
                optDto.setOptDtlNm(option.getOptDtlEntity().getOpDtlNm());
                optDto.setMin(option.getOptDtlEntity().getMin());
                optDto.setMax(option.getOptDtlEntity().getMax());
                optDto.setUnitPrice(option.getOptDtlEntity().getUnitPrice());
                return optDto;
            }).collect(Collectors.toList());

            menuDto.setOptions(optionDtos);
            return menuDto;
        }).collect(Collectors.toList());

        dto.setMenus(menuDtos);

        if (!order.getMenus().isEmpty()) {
            dto.setStoreId(order.getMenus().get(0).getStore().getStoreId());
            dto.setStoreName(order.getMenus().get(0).getStore().getStoreNm());
        }

        return dto;
    }
}
