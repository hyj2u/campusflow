package com.cnco.campusflow.order;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.user.AppUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Transactional
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/consumer")
    public ResponseEntity<CommonResponse<?>> addConsumerInfo(@AuthenticationPrincipal AppUserEntity appUser, @RequestBody ConsumerRequestDto consumerRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(orderService.addConsumerInfo(appUser, consumerRequestDto)));

    }
    @GetMapping("/consumer")
    public ResponseEntity<CommonResponse<?>> addConsumerInfo(@AuthenticationPrincipal AppUserEntity appUser) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(orderService.getConsumerInfo(appUser)));

    }
    @PostMapping("/consumer/addr")
    public ResponseEntity<CommonResponse<?>> addConsumerAddr(@AuthenticationPrincipal AppUserEntity appUser, @RequestBody ConsumerAddressDto addressDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(orderService.addConsumerAddress(appUser, addressDto)));

    }
    @GetMapping("/consumer/addr")
    public ResponseEntity<CommonResponse<?>> addConsumerAddr(@AuthenticationPrincipal AppUserEntity appUser) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(orderService.getConsumerAddress(appUser)));

    }
    @DeleteMapping("/consumer/addr/{orderAddrId}")
    public ResponseEntity<CommonResponse<?>> deleteConsumerAddr(@PathVariable Long orderAddrId) {
        orderService.deleteConsumerAddress(orderAddrId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PostMapping
    public ResponseEntity<CommonResponse<?>> addOrder( @RequestBody OrderRequestDto orderRequestDto) {
        Map<String, Object> data = new HashMap<>();
        data.put("data", orderService.addOrder(orderRequestDto).getOrderId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(data));

    }
  /*  @GetMapping("/history")
    public ResponseEntity<CommonResponse<?>> getOrders(@AuthenticationPrincipal AppUserEntity appUser ) {


    }*/

}
