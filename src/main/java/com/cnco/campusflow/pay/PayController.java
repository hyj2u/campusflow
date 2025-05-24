package com.cnco.campusflow.pay;

import com.cnco.campusflow.common.CommonResponse;
import com.cnco.campusflow.order.ConsumerAddressDto;
import com.cnco.campusflow.order.ConsumerRequestDto;
import com.cnco.campusflow.order.OrderRequestDto;
import com.cnco.campusflow.order.OrderService;
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
@RequestMapping("/pay")
@RequiredArgsConstructor
@Transactional
@Tag(
    name = "Pay",
    description = """
       결제 API
        
        * KCP 결제연동
        """
)
public class PayController {
    private final PayService payService;

    @Operation(
        summary = "결제 준비요청",
        description = """
            KCP 결제 준비 요청
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "결제준비 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값"),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @PostMapping("/prepare")
    public ResponseEntity<CommonResponse<?>> preparePayment(
       @AuthenticationPrincipal AppUserEntity appUser,
        @Parameter(description = "결제 요청 정보") @RequestBody PayPrepareRequestDto payPrepareRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(payService.preparePayment(payPrepareRequestDto, appUser)));
    }
    @Operation(
            summary = "KCP 결제 승인 콜백",
            description = """
        KCP 결제 완료 후 PG사(KCP)에서 호출하는 승인 결과 처리 API입니다.

        * res_cd가 "0000"이면 결제 성공
        * orderId 기준으로 주문 상태를 '결제 완료'로 변경
        * 실패 시 주문 상태를 실패로 처리하거나 별도 로깅
        * 클라이언트가 직접 호출하는 API가 아니므로 Swagger 테스트는 불필요
        """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 성공 처리 완료"),
            @ApiResponse(responseCode = "400", description = "결제 실패 처리됨")
    })
    @PostMapping("/approve")
    public ResponseEntity<CommonResponse<?>> approvePayment(
            @Parameter(hidden = true) @RequestParam Map<String, String> params
    ) {
        String resCd = params.get("res_cd");
        String orderId = params.get("ordr_idxx");
        String tno = params.get("tno"); // 거래번호
        String amountStr = params.get("good_mny");

        if ("0000".equals(resCd)) {
            int amount = Integer.parseInt(amountStr);
            payService.completeOrder(orderId, tno, amount);
            return ResponseEntity.ok(CommonResponse.of("결제 완료"));
        } else {
            payService.failOrder(orderId, resCd, params.get("res_msg"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CommonResponse.of("결제 실패: " + params.get("res_msg")));
        }
    }

}
