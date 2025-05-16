package com.cnco.campusflow.order;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.user.AppUserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
    name = "Order",
    description = """
        주문 관리 API
        
        * 주문 생성 및 조회 기능을 제공합니다.
        * 주문자 정보, 배달 주소 관리 기능을 포함합니다.
        * JWT 인증이 필요한 API입니다.
        """
)
public class OrderController {
    private final OrderService orderService;

    @Operation(
        summary = "주문자 정보 추가",
        description = """
            인증된 사용자의 주문자 정보를 추가합니다.
            
            * 주문자 ID, 배달 주소 ID, 요청사항을 포함합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "주문자 정보 추가 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @PostMapping("/consumer")
    public ResponseEntity<CommonResponse<?>> addConsumerInfo(
        @Parameter(description = "인증된 사용자 정보") @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(description = "주문자 요청 정보") @RequestBody ConsumerRequestDto consumerRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(orderService.addConsumerInfo(appUser, consumerRequestDto)));
    }

    @Operation(
        summary = "주문자 정보 조회",
        description = """
            인증된 사용자의 주문자 정보를 조회합니다.
            
            * 주문자 ID, 배달 주소, 요청사항을 포함합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "주문자 정보 조회 성공"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @GetMapping("/consumer")
    public ResponseEntity<CommonResponse<?>> addConsumerInfo(
        @Parameter(description = "인증된 사용자 정보") @AuthenticationPrincipal AppUserEntity appUser
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(orderService.getConsumerInfo(appUser)));
    }

    @Operation(
        summary = "배달 주소 추가",
        description = """
            인증된 사용자의 배달 주소를 추가합니다.
            
            * 기본 주소, 상세 주소, 기본 배달지 여부를 포함합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "배달 주소 추가 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @PostMapping("/consumer/addr")
    public ResponseEntity<CommonResponse<?>> addConsumerAddr(
        @Parameter(description = "인증된 사용자 정보") @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(description = "배달 주소 정보") @RequestBody ConsumerAddressDto addressDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(orderService.addConsumerAddress(appUser, addressDto)));
    }

    @Operation(
        summary = "배달 주소 조회",
        description = """
            인증된 사용자의 배달 주소를 조회합니다.
            
            * 기본 주소, 상세 주소, 기본 배달지 여부를 포함합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "배달 주소 조회 성공"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @GetMapping("/consumer/addr")
    public ResponseEntity<CommonResponse<?>> addConsumerAddr(
        @Parameter(description = "인증된 사용자 정보") @AuthenticationPrincipal AppUserEntity appUser
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(orderService.getConsumerAddress(appUser)));
    }

    @Operation(
        summary = "배달 주소 삭제",
        description = """
            특정 배달 주소를 삭제합니다.
            
            * 배달 주소 ID로 해당 주소를 삭제합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "배달 주소 삭제 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @ApiResponse(responseCode = "404", description = "배달 주소를 찾을 수 없음")
    })
    @DeleteMapping("/consumer/addr/{orderAddrId}")
    public ResponseEntity<CommonResponse<?>> deleteConsumerAddr(
        @Parameter(description = "배달 주소 번호", example = "1") @PathVariable Long orderAddrId
    ) {
        orderService.deleteConsumerAddress(orderAddrId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
        summary = "주문 생성",
        description = """
            새로운 주문을 생성합니다.
            
            * 주문자 ID, 메뉴 ID 목록, 총 가격을 포함합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "주문 생성 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @PostMapping
    public ResponseEntity<CommonResponse<?>> addOrder(
        @Parameter(description = "주문 요청 정보") @RequestBody OrderRequestDto orderRequestDto
    ) {
        Map<String, Object> data = new HashMap<>();
        data.put("orderId", orderService.addOrder(orderRequestDto).getOrderId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(data));
    }

    @Operation(
        summary = "주문 상세 조회",
        description = """
            특정 주문의 상세 정보를 조회합니다.
            
            * 주문 ID로 해당 주문의 상세 정보를 조회합니다.
            * 주문자 정보, 메뉴 목록, 총 가격, 주문 상태를 포함합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "주문 상세 조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음")
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<CommonResponse<?>> getOrderDtl(
        @Parameter(description = "주문 번호", example = "1") @PathVariable Long orderId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(orderService.getOrder(orderId)));
    }

    @Operation(
        summary = "주문 내역 조회",
        description = """
            인증된 사용자의 주문 내역을 조회합니다.
            
            * 주문자 정보, 메뉴 목록, 총 가격, 주문 상태를 포함합니다.
            * JWT 인증이 필요합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "주문 내역 조회 성공"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @GetMapping("/history")
    public ResponseEntity<CommonResponse<?>> getOrders(
        @Parameter(description = "인증된 사용자 정보") @AuthenticationPrincipal AppUserEntity appUser
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(orderService.getOrderHistory(appUser)));
    }
}
