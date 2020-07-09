package com.kakaopay.interview.business.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class OrderControllerTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderService orderService;

    private Member member;
    private Order order;

    @BeforeEach
    void setUp() throws Exception {
        this.member = insertMember();
        this.order = insertOrder();
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

    @Test
    void listByOrderNo() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/v1/" + String.valueOf(member.getMemberNo()) + "/order/" + String.valueOf(order.getOrderNo()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        logger.debug("[RESULT]: " + result.getResponse().getContentAsString());
    }

    @Test
    void createOrder() throws Exception {

        Long memberNo = member.getMemberNo();
        OrderDto.PaymentDto paymentDto = OrderDto.PaymentDto.builder()
                .cardNo("1234567890")
                .cvc("030")
                .expirationDate("0426")
                .monthlyPayment(0)
                .totalAmt(11000L)
                .vatAmt(0L)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(paymentDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/v1/" + String.valueOf(memberNo) + "/order/create")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
}
