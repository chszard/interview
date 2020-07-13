package com.kakaopay.interview.business.member.entity;

import com.kakaopay.interview.common.entity.BaseEntity;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public Member(String username, String password, @Email String email, String role, boolean enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
    }
}
