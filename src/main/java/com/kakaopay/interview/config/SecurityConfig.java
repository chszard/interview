//package com.kakaopay.interview.config;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/h2-console").permitAll()
//                .antMatchers("/v1/**").permitAll()
//                .antMatchers("/members/**").authenticated()
//                .antMatchers("/admin/**").hasRole("ADMIN");
//        http.csrf().disable();
//
//        http.formLogin().loginPage("/login").defaultSuccessUrl("/loginSuccess", true);
//        http.logout().invalidateHttpSession(true).logoutSuccessUrl("/login");
//
//    }
//}
