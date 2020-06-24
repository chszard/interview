package com.kakaopay.interview.business.member.entity;


import com.kakaopay.interview.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;



@Entity
@Getter
@Setter
@Table(name = "memberInfo")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;

    private String username;
    private String password;

    @Email
    private String email;
    private String role;
    private boolean enabled;


}
