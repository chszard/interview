package com.commerce.interview.business.order.controller;

import com.commerce.interview.business.member.entity.Member;
import com.commerce.interview.business.member.service.MemberService;
import com.commerce.interview.business.order.dto.OrderDto;
import com.commerce.interview.business.order.service.OrderService;
import com.commerce.interview.utils.exceptions.UserNotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@RestController
@RequestMapping(value = "/v1/{memberNo}/order")
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;

    @ApiOperation(value = "사용자의 모든 구매내역 조회", notes = "사용자의 구매내역을 조회한다.")
    @GetMapping("/list")
    public ResponseEntity list(@PathVariable Long memberNo) {
        Member member = memberService.getMemberByMemberNo(memberNo);
        if (member == null) {
            throw new UserNotFoundException("사용자가 존재하지 않습니다. memberNo: " + memberNo);
        }

        return ResponseEntity.ok(orderService.getOrderListByMember(member));
    }

    @ApiOperation(value = "주문관리번호로 사용자의 구매내역 조회", notes = "주문관리번호로 사용자의 구매내역 조회")
    @GetMapping("/{orderNo}")
    public ResponseEntity getOrder(@PathVariable Long memberNo, @PathVariable Long orderNo) {
        Member member = memberService.getMemberByMemberNo(memberNo);
        if (member == null) {
            throw new UserNotFoundException("사용자가 존재하지 않습니다. memberNo: " + memberNo);
        }
        return ResponseEntity.ok(orderService.getOrderByOrderNo(orderNo));
    }

    @ApiOperation(value = "구매를 생성한다.", notes = "구매를 생성한다.")
    @PostMapping("/create")
    public ResponseEntity createOrder(@PathVariable Long memberNo, @Valid @RequestBody OrderDto.PaymentDto paymentDto) {
        Member member = memberService.getMemberByMemberNo(memberNo);
        if (member == null) {
            throw new UserNotFoundException("사용자가 존재하지 않습니다. memberNo: " + memberNo);
        }

        return ResponseEntity.ok(orderService.createOrder(member, paymentDto));
    }


}
