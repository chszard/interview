package com.kakaopay.interview.business.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


public class MemberDto {

    @Setter
    @Getter
    public static class CreateDto {
        @NotNull
        private String username;
        @NotNull
        private String password;

        @Email
        private String email;
        private String role;
        private boolean enabled;

        @Builder
        public CreateDto(@NotNull String username, @NotNull String password, @Email String email, String role, boolean enabled) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.role = "ROLE_USER";
            this.enabled = true;
        }
    }
}
