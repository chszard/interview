package com.commerce.interview.interview.total;

import com.commerce.interview.business.claim.dto.ClaimDto;
import com.commerce.interview.business.claim.entity.Claim;
import com.commerce.interview.business.claim.service.ClaimService;
import com.commerce.interview.business.member.entity.Member;
import com.commerce.interview.business.member.repository.MemberRepository;
import com.commerce.interview.business.order.dto.OrderDto;
import com.commerce.interview.business.order.entity.Order;
import com.commerce.interview.business.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class TotalIntegrateSenarioTest1 {
    static final Logger logger = LoggerFactory.getLogger(TotalIntegrateSenarioTest1.class);

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClaimService claimService;

    private Member member;
    private Order order;

    @BeforeEach
    void setUp() throws Exception {
        this.member = insertMember();
        this.order = insertOrder();
    }

    @Test
    public void totalPaymentTest1() throws Exception {
        logger.info("[CLAIM][START] ==== claim success1");
        insertClaim_Success1();
        logger.info("[CLAIM][PASS] ==== claim success1");

        logger.info("[CLAIM][START] ==== claim success2");
        insertClaim_Success2();
        logger.info("[CLAIM][PASS] ==== claim success2");

        logger.info("[CLAIM][START] ==== claim fail1");
        try {
            insertClaim_fail1();
        } catch (Exception e) {
            logger.info("[CLAIM][LOG]: " + e.getMessage());
        }
        logger.info("[CLAIM][PASS] ==== claim fail1");

        logger.info("[CLAIM][START] ==== claim fail2");
        try {
            insertClaim_fail2();
        } catch (Exception e) {
            logger.info("[CLAIM][LOG]: " + e.getMessage());
        }
        logger.info("[CLAIM][PASS] ==== claim fail2");

        logger.info("[CLAIM][START] ==== claim success3");
        insertClaim_Success3();
        logger.info("[CLAIM][PASS] ==== claim success3");


        logger.info("[CLAIM][START] ==== claim fail3");
        try {
            insertClaim_fail3();
        } catch (Exception e) {
            logger.info("[CLAIM][LOG]: " + e.getMessage());
        }
        logger.info("[CLAIM][PASS] ==== claim fail3");
    }

    public Member insertMember() {
        if (this.member != null) return this.member;

        Member member = new Member("user", "1234", "chszard@gmail.com", "ROLE_USER", true);
        return memberRepository.save(member);
    }

    public Order insertOrder() {
        logger.info("[START] ==== order");
        OrderDto.PaymentDto paymentDto = OrderDto.PaymentDto.builder()
                .cardNo("1234567890")
                .cvc("030")
                .expirationDate("0426")
                .monthlyPayment(0)
                .totalAmt(11000L)
                .vatAmt(1000L)
                .build();
        return orderService.createOrder(member, paymentDto);
    }

    public Claim insertClaim_Success1() throws Exception {
        ClaimDto.CancelDto cancelDto = ClaimDto.CancelDto.builder()
                .cancelTotalAmt(1100L)
                .cancelVatAmt(100L)
                .orderNo(this.order.getOrderNo())
                .build();
        return claimService.cancelOrder(member, cancelDto);
    }

    public Claim insertClaim_Success2() throws Exception {
        ClaimDto.CancelDto cancelDto = ClaimDto.CancelDto.builder()
                .cancelTotalAmt(3300L)
                .cancelVatAmt(null)
                .orderNo(this.order.getOrderNo())
                .build();
        return claimService.cancelOrder(member, cancelDto);
    }

    public Claim insertClaim_fail1() throws Exception {
        ClaimDto.CancelDto cancelDto = ClaimDto.CancelDto.builder()
                .cancelTotalAmt(7000L)
                .cancelVatAmt(null)
                .orderNo(this.order.getOrderNo())
                .build();
        return claimService.cancelOrder(member, cancelDto);
    }

    public Claim insertClaim_fail2() throws Exception {
        ClaimDto.CancelDto cancelDto = ClaimDto.CancelDto.builder()
                .cancelTotalAmt(6600L)
                .cancelVatAmt(700L)
                .orderNo(this.order.getOrderNo())
                .build();
        return claimService.cancelOrder(member, cancelDto);
    }

    public Claim insertClaim_Success3() throws Exception {
        ClaimDto.CancelDto cancelDto = ClaimDto.CancelDto.builder()
                .cancelTotalAmt(6600L)
                .cancelVatAmt(600L)
                .orderNo(this.order.getOrderNo())
                .build();
        return claimService.cancelOrder(member, cancelDto);
    }

    public Claim insertClaim_fail3() throws Exception {
        ClaimDto.CancelDto cancelDto = ClaimDto.CancelDto.builder()
                .cancelTotalAmt(100L)
                .cancelVatAmt(null)
                .orderNo(this.order.getOrderNo())
                .build();
        return claimService.cancelOrder(member, cancelDto);
    }

}
