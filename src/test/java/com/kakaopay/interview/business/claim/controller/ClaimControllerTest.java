package com.kakaopay.interview.business.claim.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.interview.business.claim.dto.ClaimDto;
import com.kakaopay.interview.business.claim.entity.Claim;
import com.kakaopay.interview.business.claim.service.ClaimService;
import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.member.repository.MemberRepository;
import com.kakaopay.interview.business.order.dto.OrderDto;
import com.kakaopay.interview.business.order.entity.Order;
import com.kakaopay.interview.business.order.service.OrderService;
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
class ClaimControllerTest {
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
    private Claim claim;

    @BeforeEach
    void setUp() throws Exception {
        this.member = insertMember();
        this.order = insertOrder();
        this.claim = insertClaim();
    }

    public Member insertMember() {
        if (this.member != null) return this.member;

        Member member = new Member();
        member.setEmail("chszard@gmail.com");
        member.setUsername("chszard");
        member.setPassword("1234");
        member.setEnabled(true);
        member.setRole("ROLE_USER");

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
    void listByClaimNo() throws Exception {
      RequestBuilder request = MockMvcRequestBuilders
                .get("/v1/"+String.valueOf(member.getMemberNo())+"/claim/"+String.valueOf(claim.getClaimNo()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        logger.debug("[RESULT]: "+result.getResponse().getContentAsString());
    }

    @Test
    void listByMember() throws Exception {

        Long memberNo = 1L;
        RequestBuilder request = MockMvcRequestBuilders
                .get("/v1/"+String.valueOf(memberNo)+"/claim/list")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        logger.debug("[RESULT]: "+result.getResponse().getContentAsString());
    }

    @Test
    void listByOrder() throws Exception {
        Long memberNo = 1L;
        Long orderNo = 1L;
        RequestBuilder request = MockMvcRequestBuilders
                .get("/v1/"+String.valueOf(memberNo)+"/claim/order/"+String.valueOf(orderNo))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        logger.debug("[RESULT]: "+result.getResponse().getContentAsString());
    }

    @Test
    void cancelOrder() throws Exception{

        Long memberNo = member.getMemberNo();
        ClaimDto.CancelDto cancelDto = ClaimDto.CancelDto.builder()
                .cancelTotalAmt(1000L)
                .cancelVatAmt(0L)
                .orderNo(this.order.getOrderNo())
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(cancelDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/v1/"+String.valueOf(memberNo)+"/claim/create")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
}