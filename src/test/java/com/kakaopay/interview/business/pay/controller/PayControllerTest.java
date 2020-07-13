package com.kakaopay.interview.business.pay.controller;

import com.kakaopay.interview.business.claim.dto.ClaimDto;
import com.kakaopay.interview.business.claim.entity.Claim;
import com.kakaopay.interview.business.claim.service.ClaimService;
import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.member.repository.MemberRepository;
import com.kakaopay.interview.business.order.dto.OrderDto;
import com.kakaopay.interview.business.order.entity.Order;
import com.kakaopay.interview.business.order.service.OrderService;
import com.kakaopay.interview.business.pay.entity.Pay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PayControllerTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClaimService claimService;

    private Member member;
    private Order order;
    private Pay pay;
    private Claim claim;


    @BeforeEach
    void setUp() throws Exception {
        this.member = insertMember();
        this.order = insertOrder();
        this.claim = insertClaim();
    }

    public Member insertMember() {
        if (this.member != null) return this.member;
        Member member = new Member("user", "1234", "chszard@gmail.com", "ROLE_USER", true);
        return memberRepository.save(member);
    }

    public Order insertOrder() {
        OrderDto.PaymentDto paymentDto = OrderDto.PaymentDto.builder()
                .cardNo("1234567890")
                .cvc("030")
                .expirationDate("0426")
                .monthlyPayment(0)
                .totalAmt(11000L)
                .vatAmt(0L)
                .build();
        return orderService.createOrder(member, paymentDto);
    }

    public Claim insertClaim() throws Exception {
        ClaimDto.CancelDto cancelDto = ClaimDto.CancelDto.builder()
                .cancelTotalAmt(1000L)
                .cancelVatAmt(0L)
                .orderNo(this.order.getOrderNo())
                .build();
        return claimService.cancelOrder(member, cancelDto);
    }

    @Test
    void listByMember() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/v1/" + String.valueOf(member.getMemberNo()) + "/pay/list")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        logger.debug("[RESULT]: " + result.getResponse().getContentAsString());
    }

    @Test
    void listByOrderNo() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/v1/" + String.valueOf(member.getMemberNo()) + "/pay/" + String.valueOf(order.getOrderNo() + "/list"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        logger.debug("[RESULT]: " + result.getResponse().getContentAsString());
    }

    @Test
    void getPay() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/v1/" + String.valueOf(member.getMemberNo()) + "/pay/" + String.valueOf(20L))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }

    @Test
    void getPayCancel() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/v1/" + String.valueOf(member.getMemberNo()) + "/pay/cancel/" + String.valueOf(claim.getPay().getPayNo()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
}
