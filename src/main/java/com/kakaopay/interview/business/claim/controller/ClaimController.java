package com.kakaopay.interview.business.claim.controller;

import com.kakaopay.interview.business.claim.dto.ClaimDto;
import com.kakaopay.interview.business.claim.service.ClaimService;
import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.member.service.MemberService;
import com.kakaopay.interview.business.order.entity.Order;
import com.kakaopay.interview.business.order.service.OrderService;
import com.kakaopay.interview.common.handler.MessageHandler;
import com.kakaopay.interview.utils.enums.code.MessageType;
import com.kakaopay.interview.utils.exceptions.OrderNotFoundException;
import com.kakaopay.interview.utils.exceptions.UserNotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/v1/{memberNo}/claim")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;
    private final MemberService memberService;
    private final OrderService orderService;
    private final MessageHandler messageHandler;


    @ApiOperation(value = "사용자의 모든 환불내역 조회", notes = "사용자의 환불내역 조회한다.")
    @GetMapping("/list")
    public ResponseEntity list(@PathVariable Long memberNo) {
        Member member = Optional.ofNullable(memberService.getMemberByMemberNo(memberNo))
                .orElseThrow(() -> new UserNotFoundException(messageHandler.getMessage(MessageType.NOT_FOUND_USER)));

        return ResponseEntity.ok(claimService.getClaimListByMember(member));
    }

    @ApiOperation(value = "취소관리번호로 사용자의 환불내역 조회", notes = "사용자의 환불내역 조회한다.")
    @GetMapping("/{claimNo}")
    public ResponseEntity one(@PathVariable Long memberNo, @PathVariable Long claimNo) {
        Optional.ofNullable(memberService.getMemberByMemberNo(memberNo))
                .orElseThrow(() -> new UserNotFoundException(messageHandler.getMessage(MessageType.NOT_FOUND_USER)));

        return ResponseEntity.ok(claimService.getClaimByClaimNo(claimNo));
    }

    @ApiOperation(value = "주문관리번호로 사용자의 환불내역 조회", notes = "주문관리번호로 사용자의 환불내역 조회")
    @GetMapping("/order/{orderNo}")
    public ResponseEntity list(@PathVariable Long memberNo, @PathVariable Long orderNo) {
        Optional.ofNullable(memberService.getMemberByMemberNo(memberNo))
                .orElseThrow(() -> new UserNotFoundException(messageHandler.getMessage(MessageType.NOT_FOUND_USER)));

        Order order = orderService.getOrderByOrderNo(orderNo);
        if (order == null) {
            throw new OrderNotFoundException("주문이 존재하지 않습니다. orderNo:" + orderNo);
        }
        return ResponseEntity.ok(claimService.getClaimListByOrder(order));
    }

    @ApiOperation(value = "환불내역을 생성한다.", notes = "환불내역을 생성한다.")
    @PostMapping("/create")
    public ResponseEntity cancelOrder(@PathVariable Long memberNo, @RequestBody ClaimDto.CancelDto cancelDto) throws Exception {
        Member member = Optional.ofNullable(memberService.getMemberByMemberNo(memberNo))
                .orElseThrow(() -> new UserNotFoundException(messageHandler.getMessage(MessageType.NOT_FOUND_USER)));
        return ResponseEntity.ok(claimService.cancelOrder(member, cancelDto));
    }
}
