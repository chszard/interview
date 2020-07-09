package com.kakaopay.interview.business.claim.controller;

import com.kakaopay.interview.business.claim.dto.ClaimDto;
import com.kakaopay.interview.business.claim.service.ClaimService;
import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.member.service.MemberService;
import com.kakaopay.interview.business.order.entity.Order;
import com.kakaopay.interview.business.order.service.OrderService;
import com.kakaopay.interview.utils.exceptions.OrderNotFoundException;
import com.kakaopay.interview.utils.exceptions.UserNotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/v1/{memberNo}/claim")
public class ClaimController {

    private ClaimService claimService;
    private MemberService memberService;
    private OrderService orderService;

    public ClaimController(ClaimService claimService, MemberService memberService, OrderService orderService) {
        this.claimService = claimService;
        this.memberService = memberService;
        this.orderService = orderService;
    }

    @ApiOperation(value = "사용자의 모든 환불내역 조회", notes = "사용자의 환불내역 조회한다.")
    @GetMapping("/list")
    public ResponseEntity list(@PathVariable Long memberNo) {
        Member member = memberService.getMemberByMemberNo(memberNo);
        if (member == null) {
            throw new UserNotFoundException("사용자가 존재하지 않습니다. memberNo: " + memberNo);
        }

        return ResponseEntity.ok(claimService.getClaimListByMember(member));
    }

    @ApiOperation(value = "취소관리번호로 사용자의 환불내역 조회", notes = "사용자의 환불내역 조회한다.")
    @GetMapping("/{claimNo}")
    public ResponseEntity one(@PathVariable Long memberNo, @PathVariable Long claimNo) {
        Member member = memberService.getMemberByMemberNo(memberNo);
        if (member == null) {
            throw new UserNotFoundException("사용자가 존재하지 않습니다. memberNo: " + memberNo);
        }

        return ResponseEntity.ok(claimService.getClaimByClaimNo(claimNo));
    }

    @ApiOperation(value = "주문관리번호로 사용자의 환불내역 조회", notes = "주문관리번호로 사용자의 환불내역 조회")
    @GetMapping("/order/{orderNo}")
    public ResponseEntity list(@PathVariable Long memberNo, @PathVariable Long orderNo) {
        Member member = memberService.getMemberByMemberNo(memberNo);
        if (member == null) {
            throw new UserNotFoundException("사용자가 존재하지 않습니다. memberNo: " + memberNo);
        }

        Order order = orderService.getOrderByOrderNo(orderNo);
        if (order == null) {
            throw new OrderNotFoundException("주문이 존재하지 않습니다. orderNo:" + orderNo);
        }

        return ResponseEntity.ok(claimService.getClaimListByOrder(order));
    }

    @ApiOperation(value = "환불내역을 생성한다.", notes = "환불내역을 생성한다.")
    @PostMapping("/create")
    public ResponseEntity cancelOrder(@PathVariable Long memberNo, @RequestBody ClaimDto.CancelDto cancelDto) throws Exception {
        Member member = memberService.getMemberByMemberNo(memberNo);
        if (member == null) {
            throw new UserNotFoundException("사용자가 존재하지 않습니다. memberNo: " + memberNo);
        }

        return ResponseEntity.ok(claimService.cancelOrder(member, cancelDto));
    }
}
