package com.kakaopay.interview.total;

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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@AutoConfigureMockMvc
public class syncTest {
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

    // 같은 카드번호 + 주문생성시간으로  중복 결제 방어
    // BaseEntity createdDate 주석 지우고
    // 주문 생성시에 강제로 동일 localdatetime 고정한 뒤 테스트.
    // Duplicate entry '2020-06-22 15:00:00.000000-MTIzNDU2Nzg5MDwwNDI2PDAzMA==' for key 'UK4nurd7rg8jfsbqvleq0h04pjm'
    // Duplicate entry '2020-06-22 15:00:00.000000-MTIzNDU2Nzg5MDwwNDI2PDAzMA==' for key 'UK4nurd7rg8jfsbqvleq0h04pjm'
    @Test
    public void syncOrderTest() throws Exception {
        Callable<Void> callable1 = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                insertSyncOrder();
                return null;
            }
        };

        Callable<Void> callable2 = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                insertSyncOrder();
                return null;
            }
        };

        List<Callable<Void>> taskList = new ArrayList<Callable<Void>>();
        taskList.add(callable1);
        taskList.add(callable2);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            executor.invokeAll(taskList);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }

    // 동시에 같은 취소 호출 방어
    @Test
    public void syncCancelOrderTest() throws Exception {

        Callable<Void> callable1 = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                insertCancelOrder1();
                return null;
            }
        };

        Callable<Void> callable2 = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                insertCancelOrder1();
                return null;
            }
        };

        List<Callable<Void>> taskList = new ArrayList<Callable<Void>>();
        taskList.add(callable1);
        taskList.add(callable2);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            executor.invokeAll(taskList);
        } catch (Exception e) {
            throw new Exception(e);
        }
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
                .totalAmt(20000L)
                .vatAmt(null)
                .build();
        return orderService.createOrder(member, paymentDto);
    }

    public Order insertSyncOrder() {
        logger.info("[ORDER][START] ==== sync");
        OrderDto.PaymentDto paymentDto = OrderDto.PaymentDto.builder()
                .cardNo("1234567890")
                .cvc("030")
                .expirationDate("0426")
                .monthlyPayment(0)
                .totalAmt(20000L)
                .vatAmt(null)
                .build();
        return orderService.createOrder(member, paymentDto);
    }

    public Claim insertCancelOrder1() throws Exception {
        ClaimDto.CancelDto cancelDto = ClaimDto.CancelDto.builder()
                .cancelTotalAmt(20000L)
                .cancelVatAmt(null)
                .orderNo(this.order.getOrderNo())
                .build();
        return claimService.cancelOrder(member, cancelDto);
    }

    public Claim insertCancelOrder2() throws Exception {
        ClaimDto.CancelDto cancelDto = ClaimDto.CancelDto.builder()
                .cancelTotalAmt(20000L)
                .cancelVatAmt(null)
                .orderNo(this.order.getOrderNo())
                .build();
        return claimService.cancelOrder(member, cancelDto);
    }

}
