package com.kakaopay.interview.business.pay.controller;

import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.member.service.MemberService;
import com.kakaopay.interview.business.order.entity.Order;
import com.kakaopay.interview.business.order.service.OrderService;
import com.kakaopay.interview.business.pay.service.PayService;
import com.kakaopay.interview.utils.exceptions.OrderNotFoundException;
import com.kakaopay.interview.utils.exceptions.UserNotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@RestController
@RequestMapping(value = "/v1/{memberNo}/pay")
public class PayController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final PayService payService;

    @ApiOperation(value = "사용자의 모든 결제내역 조회", notes = "사용자의 결제내역 조회한다.")
    @GetMapping("/list")
    public ResponseEntity list(@PathVariable Long memberNo) {
        Member member = memberService.getMemberByMemberNo(memberNo);
        if (member == null) {
            throw new UserNotFoundException("사용자가 존재하지 않습니다. memberNo: "+memberNo);
        }

        return ResponseEntity.ok(payService.getPayListByMember(member));
    }

    @ApiOperation(value = "주문관리번호로 사용자의 결제내역 조회", notes = "주문관리번호로 사용자의 결제내역 조회")
    @GetMapping("/{orderNo}/list")
    public ResponseEntity list(@PathVariable Long memberNo, @PathVariable Long orderNo) {
        Member member = memberService.getMemberByMemberNo(memberNo);
        if (member == null) {
            throw new UserNotFoundException("사용자가 존재하지 않습니다. memberNo: "+memberNo);
        }

        Order order = orderService.getOrderByOrderNo(orderNo);
        if (order == null) {
            throw new OrderNotFoundException("주문이 존재하지 않습니다. orderNo: "+orderNo);
        }
        return ResponseEntity.ok(payService.getPayListByOrder(order));
    }

    @ApiOperation(value = "결제관리번호로 사용자의 결제내역 조회", notes = "결제관리번호로 사용자의 결제내역 조회")
    @GetMapping("/{payNo}")
    public ResponseEntity getPay(@PathVariable Long memberNo, @PathVariable Long payNo) {
        Member member = memberService.getMemberByMemberNo(memberNo);
        if (member == null) {
            throw new UserNotFoundException("사용자가 존재하지 않습니다. memberNo: "+memberNo);
        }

        return ResponseEntity.ok(payService.getPayByPayNo(payNo));
    }

    @ApiOperation(value = "결제관리번호로 사용자의 결제취소내역 조회", notes = "결제관리번호로 사용자의 결제취소내역 조회")
    @GetMapping("/cancel/{payNo}")
    public ResponseEntity getPayCancel(@PathVariable Long memberNo, @PathVariable Long payNo) {
        Member member = memberService.getMemberByMemberNo(memberNo);
        if (member == null) {
            throw new UserNotFoundException("사용자가 존재하지 않습니다. memberNo: "+memberNo);
        }

        return ResponseEntity.ok(payService.getPayCancelByPayNo(payNo));
    }

}
