package com.cnco.campusflow.pay;

import com.cnco.campusflow.menu.*;
import com.cnco.campusflow.order.*;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.user.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PayService {
    @Value("${kcp.site_cd}")
    private String siteCd;

    private final OrderRepository orderRepository;

    public PayPrepareResponseDto preparePayment(PayPrepareRequestDto dto, AppUserEntity appUser) {

        PayPrepareResponseDto response = new PayPrepareResponseDto();
        response.setSiteCd(siteCd); // KCP 테스트 site code
        response.setOrderId(dto.getOrderId().toString());
        response.setGoodName(dto.getGoodName());
        response.setAmount(dto.getAmount());
        response.setBuyrName(appUser.getAppUserName());
        response.setBuyrTel(appUser.getPhone());
        response.setBuyrMail(appUser.getEmail());

        return response;
    }
    public void completeOrder(String orderId, String tno, int amount) {
        OrderEntity order = orderRepository.findById(Long.parseLong((orderId)))
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다. 주문번호: " + orderId));

        if (!"WAITING".equals(order.getOrderStatus())) {
            log.warn("이미 처리된 주문입니다: {}", orderId);
            return;
        }
        order.setOrderStatus("PAID");
        order.setPaymentTid(tno);
        order.setPaidAmount(amount);
        order.setPaidAt(LocalDateTime.now());
        orderRepository.save(order);
        log.info("주문 결제 성공 처리 완료 - 주문번호: {}, 거래번호: {}", orderId, tno);
    }
    public void failOrder(String orderId, String resCd, String resMsg) {
        OrderEntity order = orderRepository.findById(Long.parseLong((orderId)))
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다. 주문번호: " + orderId));

        // 이미 결제된 주문은 실패 처리하지 않음
        if ("PAID".equals(order.getOrderStatus())) {
            log.warn("❗ 이미 결제된 주문입니다. 실패 처리 스킵 - 주문번호: {}", orderId);
            return;
        }

        order.setOrderStatus("FAILED");
        order.setFailReason("KCP 실패 코드: " + resCd + " / " + resMsg);
        order.setFailAt(LocalDateTime.now());
        orderRepository.save(order);

        log.warn("주문 결제 실패 처리 완료 - 주문번호: {}, 실패 사유: {}", orderId, resMsg);
    }


}
