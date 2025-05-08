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
@Tag(name = "Order", description = "Order management APIs")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Add consumer information", description = "Adds consumer information for the authenticated user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Consumer information added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/consumer")
    public ResponseEntity<CommonResponse<?>> addConsumerInfo(
        @Parameter(description = "Authenticated user details") @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(description = "Consumer request details") @RequestBody ConsumerRequestDto consumerRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(orderService.addConsumerInfo(appUser, consumerRequestDto)));
    }

    @Operation(summary = "Get consumer information", description = "Retrieves consumer information for the authenticated user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consumer information retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/consumer")
    public ResponseEntity<CommonResponse<?>> addConsumerInfo(
        @Parameter(description = "Authenticated user details") @AuthenticationPrincipal AppUserEntity appUser
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(orderService.getConsumerInfo(appUser)));
    }

    @Operation(summary = "Add consumer address", description = "Adds a consumer address for the authenticated user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Consumer address added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/consumer/addr")
    public ResponseEntity<CommonResponse<?>> addConsumerAddr(
        @Parameter(description = "Authenticated user details") @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(description = "Consumer address details") @RequestBody ConsumerAddressDto addressDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(orderService.addConsumerAddress(appUser, addressDto)));
    }

    @Operation(summary = "Get consumer address", description = "Retrieves the consumer address for the authenticated user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consumer address retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/consumer/addr")
    public ResponseEntity<CommonResponse<?>> addConsumerAddr(
        @Parameter(description = "Authenticated user details") @AuthenticationPrincipal AppUserEntity appUser
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(orderService.getConsumerAddress(appUser)));
    }

    @Operation(summary = "Delete consumer address", description = "Deletes a specific consumer address.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Consumer address deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @DeleteMapping("/consumer/addr/{orderAddrId}")
    public ResponseEntity<CommonResponse<?>> deleteConsumerAddr(
        @Parameter(description = "Order address ID") @PathVariable Long orderAddrId
    ) {
        orderService.deleteConsumerAddress(orderAddrId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Add order", description = "Creates a new order with the provided details.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Order created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<CommonResponse<?>> addOrder(
        @Parameter(description = "Order request details") @RequestBody OrderRequestDto orderRequestDto
    ) {
        Map<String, Object> data = new HashMap<>();
        data.put("orderId", orderService.addOrder(orderRequestDto).getOrderId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(data));
    }

    @Operation(summary = "Get order details", description = "Retrieves the details of a specific order.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order details retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<CommonResponse<?>> getOrderDtl(
        @Parameter(description = "Order ID") @PathVariable Long orderId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(orderService.getOrder(orderId)));
    }

    @Operation(summary = "Get order history", description = "Retrieves the order history for the authenticated user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order history retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/history")
    public ResponseEntity<CommonResponse<?>> getOrders(
        @Parameter(description = "Authenticated user details") @AuthenticationPrincipal AppUserEntity appUser
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of(orderService.getOrderHistory(appUser)));
    }
}
