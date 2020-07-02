package com.kakaopay.interview.business.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.interview.business.member.dto.MemberDto;
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

@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MockMvc mockMvc;

    @Test
    void createMember() throws Exception{

        MemberDto.CreateDto createDto = MemberDto.CreateDto.builder()
                .username("username")
                .password("1234")
                .email("aaa@aaa.com")
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(createDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/v1/member/create")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        logger.debug("[RESULT]: "+result.getResponse().getContentAsString());
    }
}